package io.github.nozyx12.opentoast.style;

import java.awt.*;

/**
 * Represents a dark style configuration for a toast notification.
 * This style features a dark background with white text for both the title and message.
 */
public class DarkToastStyle extends ToastStyle {
    /**
     * Constructs a new DarkToastStyle with the specified parameters.
     *
     * @param titleFont    The font used for the title text.
     * @param messageFont  The font used for the message text.
     * @param cornerRadius The radius of the corners of the notification.
     */
    public DarkToastStyle(Font titleFont, Font messageFont, int cornerRadius) {
        super(new Color(73, 72, 72), Color.WHITE, Color.WHITE, titleFont, messageFont, cornerRadius);
    }
}
