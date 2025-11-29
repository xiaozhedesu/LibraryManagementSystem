package club.xiaozhe.library.gui.components;

import javax.swing.*;

/**
 * 为了复用而提出的选择框
 */
public class BookFieldSearchBox extends JComboBox<String> {
    public BookFieldSearchBox() {
        setModel(new DefaultComboBoxModel<>(BookFieldArrays.SHOW_MODES));
    }
}
