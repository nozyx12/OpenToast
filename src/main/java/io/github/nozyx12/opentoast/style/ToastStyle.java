package io.github.nozyx12.opentoast.style;

import java.awt.*;

/**
 * Represents the style configuration for a toast notification.
 * This class defines the appearance of the notification, including colors, fonts, and corner radius.
 */
public class ToastStyle {
    private final Color backgroundColor;
    private final Color textColor;
    private final Color titleColor;
    private final Font titleFont;
    private final Font messageFont;
    private final int cornerRadius;

    /**
     * Constructs a new ToastStyle with the specified parameters.
     *
     * @param backgroundColor The background color of the notification.
     * @param textColor       The color of the text in the notification.
     * @param titleColor      The color of the title text in the notification.
     * @param titleFont       The font used for the title text.
     * @param messageFont     The font used for the message text.
     * @param cornerRadius    The radius of the corners of the notification.
     */
    public ToastStyle(Color backgroundColor, Color textColor, Color titleColor, Font titleFont, Font messageFont, int cornerRadius) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.titleColor = titleColor;
        this.titleFont = titleFont;
        this.messageFont = messageFont;
        this.cornerRadius = cornerRadius;
    }

    /**
     * Returns the background color of the notification.
     *
     * @return The background color.
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Returns the text color of the notification.
     *
     * @return The text color.
     */
    public Color getTextColor() {
        return this.textColor;
    }

    /**
     * Returns the title color of the notification.
     *
     * @return The title color.
     */
    public Color getTitleColor() {
        return this.titleColor;
    }

    /**
     * Returns the font used for the title text.
     *
     * @return The title font.
     */
    public Font getTitleFont() {
        return this.titleFont;
    }

    /**
     * Returns the font used for the message text.
     *
     * @return The message font.
     */
    public Font getMessageFont() {
        return this.messageFont;
    }

    /**
     * Returns the radius of the corners of the notification.
     *
     * @return The corner radius.
     */
    public int getCornerRadius() {
        return this.cornerRadius;
    }
}
