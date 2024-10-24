package io.github.nozyx12.opentoast.style;

import java.awt.*;

public class DarkToastStyle extends ToastStyle {
    public DarkToastStyle(Font titleFont, Font messageFont, int cornerRadius) {
        super(new Color(73, 72, 72), Color.WHITE, Color.WHITE, titleFont, messageFont, cornerRadius);
    }
}
