package club.xiaozhe.library.gui.pages;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.service.BooksService;

import javax.swing.*;
import java.sql.SQLException;

public class DeletePage extends JPanel {
    public static final String[] SHOW_MODES = {
            "id", "书名", "作者", "出版社", "分类"
    };
    public static final String[] FIELDS = {
            "id", "name", "author", "publisher", "category"
    };
    JComboBox<String> select;
    JTextField input;

    public DeletePage() {
        add(createSelectBox());
        add(createInputArea());
        add(createSubmitButton());
    }

    private JComboBox<String> createSelectBox() {
        select = new JComboBox<>();
        select.setModel(new DefaultComboBoxModel<>(SHOW_MODES));
        return select;
    }

    private JTextField createInputArea() {
        input = new JTextField(30);
        input.addActionListener(e -> deleteBook());
        return input;
    }

    private JButton createSubmitButton() {
        JButton submit = new JButton("删除");
        submit.addActionListener(e -> deleteBook());
        return submit;
    }

    private boolean doubleCheck() {
        int result = JOptionPane.showConfirmDialog(
                DeletePage.this,
                "在删除前请确保查询过删除的条目，并确保无输入错误，确认无误后请按是。",
                "确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.OK_OPTION;
    }

    private void deleteBook() {
        if ("".equals(input.getText()) || !doubleCheck())
            return;

        try {
            int row = BooksService.delete(FIELDS[select.getSelectedIndex()], input.getText());
            JOptionPane.showMessageDialog(DeletePage.this,
                    "删除了" + row + "项。",
                    "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(DeletePage.this,
                    "数据库发生错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        } catch (BookBusinessException e) {
            JOptionPane.showMessageDialog(DeletePage.this,
                    "发生错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
