package io.github.nozyx12.opentoast.modifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A ToastModifier implementation that animates toast notifications with a swipe effect.
 * The notification swipes in from the right when displayed and swipes out to the right when closed.
 */
public class SwipeToastModifier implements ToastModifier {
    private final int swipeSpeed;

    /**
     * Constructs a SwipeToastModifier with a specified swipe speed.
     *
     * @param swipeSpeed The speed of the swipe animation in milliseconds.
     */
    public SwipeToastModifier(int swipeSpeed) {
        this.swipeSpeed = swipeSpeed;
    }

    /**
     * Constructs a SwipeToastModifier with a default swipe speed of 200 milliseconds.
     */
    public SwipeToastModifier() {
        this(200);
    }

    /**
     * Called when the toast notification is displayed.
     * This method triggers the swipe-in animation.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    @Override
    public void onDisplay(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        this.animateSwipeIn(dialog);
    }

    /**
     * Called when the toast notification is closing.
     * This method triggers the swipe-out animation.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    @Override
    public void onClose(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        this.animateSwipeOut(dialog);
    }

    /**
     * Animates the swipe-in effect for the toast notification.
     *
     * @param dialog The JDialog representing the toast notification.
     */
    private void animateSwipeIn(JDialog dialog) {
        Rectangle screenBounds = dialog.getGraphicsConfiguration().getBounds();
        int startX = screenBounds.width;
        int endX = dialog.getX();
        int startY = dialog.getY();

        Timer timer = new Timer(10, new ActionListener() {
            long startTime = System.currentTimeMillis();

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                float progress = Math.min(1f, (float) elapsedTime / SwipeToastModifier.this.swipeSpeed);

                int currentX = startX - (int) ((startX - endX) * progress);
                dialog.setLocation(currentX, startY);

                if (progress >= 1f) ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    /**
     * Animates the swipe-out effect for the toast notification.
     *
     * @param dialog The JDialog representing the toast notification.
     */
    private void animateSwipeOut(JDialog dialog) {
        Rectangle screenBounds = dialog.getGraphicsConfiguration().getBounds();
        int startX = dialog.getX();
        int endX = screenBounds.width;
        int startY = dialog.getY();

        new Timer(10, new ActionListener() {
            long startTime = System.currentTimeMillis();

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                float progress = Math.min(1f, (float) elapsedTime / SwipeToastModifier.this.swipeSpeed);

                int currentX = startX + (int) ((endX - startX) * progress);
                dialog.setLocation(currentX, startY);

                if (progress >= 1f) {
                    ((Timer) e.getSource()).stop();
                    dialog.dispose();
                }
            }
        }).start();
    }
}
