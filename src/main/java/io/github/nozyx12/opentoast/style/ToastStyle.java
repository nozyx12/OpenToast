package io.github.nozyx12.opentoast.style;

import java.awt.*;

public class ToastStyle {
    private final Color backgroundColor;
    private final Color textColor;
    private final Color titleColor;
    private final Font titleFont;
    private final Font messageFont;
    private final int cornerRadius;

    public ToastStyle(Color backgroundColor, Color textColor, Color titleColor, Font titleFont, Font messageFont, int cornerRadius) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.titleColor = titleColor;
        this.titleFont = titleFont;
        this.messageFont = messageFont;
        this.cornerRadius = cornerRadius;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public Color getTitleColor() {
        return this.titleColor;
    }

    public Font getTitleFont() {
        return this.titleFont;
    }

    public Font getMessageFont() {
        return this.messageFont;
    }

    public int getCornerRadius() {
        return this.cornerRadius;
    }
}
