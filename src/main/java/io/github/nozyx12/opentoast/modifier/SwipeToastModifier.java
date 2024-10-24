package io.github.nozyx12.opentoast.modifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwipeToastModifier implements ToastModifier {
    private final int swipeSpeed;

    public SwipeToastModifier(int swipeSpeed) {
        this.swipeSpeed = swipeSpeed;
    }

    public SwipeToastModifier() {
        this(200);
    }

    @Override
    public void onDisplay(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        animateSwipeIn(dialog);
    }

    @Override
    public void onClose(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        animateSwipeOut(dialog);
    }

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
