package io.github.nozyx12.opentoast.style;

import java.awt.*;

/**
 * Represents a light style configuration for a toast notification.
 * This style features a white background with black text for both the title and message.
 */
public class LightToastStyle extends ToastStyle {
    /**
     * Constructs a new LightToastStyle with the specified parameters.
     *
     * @param titleFont    The font used for the title text.
     * @param messageFont  The font used for the message text.
     * @param cornerRadius The radius of the corners of the notification.
     */
    public LightToastStyle(Font titleFont, Font messageFont, int cornerRadius) {
        super(Color.WHITE, Color.BLACK, Color.BLACK, titleFont, messageFont, cornerRadius);
    }
}
