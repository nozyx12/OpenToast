package io.github.nozyx12.opentoast.modifier;

import javax.swing.*;

/**
 * An interface for modifying the appearance and behavior of toast notifications.
 * Implementing classes can define custom actions to perform when the toast is displayed and closed.
 */
public interface ToastModifier {
    /**
     * Called when the toast notification is displayed.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    void onDisplay(JPanel panel);

    /**
     * Called when the toast notification is closing.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    void onClose(JPanel panel);
}
