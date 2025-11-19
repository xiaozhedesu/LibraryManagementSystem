package club.xiaozhe.library.gui.components;

import javax.swing.*;

/**
 * 提供给添加页面使用，包装后的带说明标签的一行输入框
 */
public class InputLinePanel extends JPanel {
    private final JTextField input;

    public InputLinePanel(String label) {
        add(new JLabel(label));
        add(input = new JTextField(20));
    }

    public String getText() {
        return input.getText();
    }
}
