package io.github.nozyx12.opentoast;

import io.github.nozyx12.opentoast.modifier.ToastModifier;
import io.github.nozyx12.opentoast.style.ToastStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Represents a toast notification that can be displayed on the screen.
 * The notification has a customizable appearance and can automatically close after a specified duration.
 */
public class ToastNotification {
    private final int autoClose = 5000;

    private final JDialog dialog;
    private final CountDownLatch latch;
    private final ToastStyle style;
    public static final List<JDialog> activeNotifications = new ArrayList<>();
    private static final int MARGIN = 10;
    private final List<ToastModifier> modifiers;

    /**
     * Constructs a new ToastNotification instance.
     *
     * @param title     The title of the notification.
     * @param message   The message to be displayed in the notification.
     * @param icon      An icon to display alongside the message. Can be null.
     * @param style     The style configuration for the notification.
     * @param modifiers A list of modifiers to customize the notification behavior. Can be null.
     */
    public ToastNotification(String title, String message, Icon icon, ToastStyle style, List<ToastModifier> modifiers) {
        this.latch = new CountDownLatch(1);

        if (modifiers != null) this.modifiers = modifiers;
        else this.modifiers = new ArrayList<>();

        this.style = style;
        this.dialog = this.createDialog(title, message, icon);
        this.setPosition();
        this.dialog.setOpacity(0f);
        this.dialog.setAlwaysOnTop(true);
    }

    /**
     * Displays the toast notification with a fade-in effect.
     */
    public void display() {
        this.fadeIn();
    }

    /**
     * Closes the toast notification with a fade-out effect.
     */
    public void close() {
        this.fadeOut();
    }

    /**
     * Blocks the current thread until the notification is closed.
     */
    public void waitFor() {
        try {
            this.latch.await();
        } catch (InterruptedException ignored) {}
    }

    /**
     * Creates the JDialog for the toast notification.
     *
     * @param title   The title of the notification.
     * @param message The message to be displayed in the notification.
     * @param icon    An icon to display alongside the message. Can be null.
     * @return The created JDialog for the notification.
     */
    private JDialog createDialog(String title, String message, Icon icon) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new RoundedPane(style);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topBar = new JPanel();
        topBar.setBackground(style.getBackgroundColor());
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        topBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Notification: " + title);
        titleLabel.setForeground(style.getTitleColor());
        titleLabel.setFont(style.getTitleFont());
        topBar.add(titleLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> fadeOut());
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        topBar.add(closeButton, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(style.getTextColor());
        messageLabel.setFont(style.getMessageFont());
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(messageLabel, BorderLayout.CENTER);

        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            panel.add(iconLabel, BorderLayout.WEST);
        }

        dialog.setContentPane(panel);
        dialog.pack();

        return dialog;
    }

    /**
     * Sets the position of the notification on the screen.
     * The notification will appear in the bottom right corner of the screen.
     * If there is not enough space, a ToastStackOverflowException will be thrown.
     */
    private void setPosition() {
        GraphicsConfiguration config = this.dialog.getGraphicsConfiguration();
        Rectangle screenBounds = config.getBounds();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int availableHeight = screenBounds.height - screenInsets.bottom;

        int dialogWidth = this.dialog.getWidth();
        int dialogHeight = this.dialog.getHeight();

        int x = screenBounds.width - dialogWidth - MARGIN;
        int y = availableHeight - dialogHeight - MARGIN;

        for (JDialog activeDialog : activeNotifications) y -= (activeDialog.getHeight() + MARGIN);

        if (y < 0) throw new ToastStackOverflowException();

        this.dialog.setLocation(x, y);
        activeNotifications.add(this.dialog);
    }

    /**
     * Fades in the notification, making it visible.
     * The notification will also start a timer for automatic closing after a predefined duration.
     */
    private void fadeIn() {
        this.dialog.setVisible(true);

        JPanel panel = (JPanel) this.dialog.getContentPane();
        for (ToastModifier modifier : this.modifiers) modifier.onDisplay(panel);

        this.dialog.pack();

        CountDownLatch fadeInLatch = new CountDownLatch(1);

        new Timer(10, new ActionListener() {
            float opacity = 0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 1f) {
                    opacity += 0.05f;
                    opacity = Math.min(opacity, 1f);
                    ToastNotification.this.dialog.setOpacity(opacity);
                } else {
                    ((Timer) e.getSource()).stop();
                    fadeInLatch.countDown();
                    new Timer(ToastNotification.this.autoClose, e1 -> fadeOut()).start();
                }
            }
        }).start();

        try {
            fadeInLatch.await();
        } catch (InterruptedException ignored) {}
    }

    /**
     * Fades out the notification, making it invisible.
     * The notification is disposed of after fading out.
     */
    private void fadeOut() {
        JPanel panel = (JPanel) this.dialog.getContentPane();
        for (ToastModifier modifier : this.modifiers) modifier.onClose(panel);

        this.dialog.pack();

        new Timer(25, new ActionListener() {
            float opacity = 1f;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (this.opacity > 0f) {
                    this.opacity -= 0.05f;
                    this.opacity = Math.max(opacity, 0f);
                    ToastNotification.this.dialog.setOpacity(opacity);
                } else {
                    ToastNotification.this.dialog.dispose();
                    ((Timer) e.getSource()).stop();
                    activeNotifications.remove(ToastNotification.this.dialog);
                    ToastNotification.this.latch.countDown();
                }
            }
        }).start();
    }

    /**
     * A custom JPanel that paints a rounded background for the toast notification.
     */
    private static class RoundedPane extends JPanel {
        private final ToastStyle style;

        /**
         * Constructs a new RoundedPane with the specified style.
         *
         * @param style The style configuration for the pane.
         */
        public RoundedPane(ToastStyle style) {
            this.style = style;
            this.setOpaque(false);
            this.setLayout(new BorderLayout());
        }

        /**
         * Paints the component with a rounded rectangle background.
         *
         * @param g The graphics context to use for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(style.getBackgroundColor());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, style.getCornerRadius(), style.getCornerRadius());
            super.paintComponent(g);
            g2.dispose();
        }
    }
}
