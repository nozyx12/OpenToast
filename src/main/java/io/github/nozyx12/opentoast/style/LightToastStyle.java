package io.github.nozyx12.opentoast.style;

import java.awt.*;

public class LightToastStyle extends ToastStyle {
    public LightToastStyle(Font titleFont, Font messageFont, int cornerRadius) {
        super(Color.WHITE, Color.BLACK, Color.BLACK, titleFont, messageFont, cornerRadius);
    }
}
