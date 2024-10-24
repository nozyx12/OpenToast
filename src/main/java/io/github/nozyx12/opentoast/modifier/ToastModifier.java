package io.github.nozyx12.opentoast.modifier;

import javax.swing.*;

public interface ToastModifier {
    void onDisplay(JPanel panel);

    void onClose(JPanel panel);
}
