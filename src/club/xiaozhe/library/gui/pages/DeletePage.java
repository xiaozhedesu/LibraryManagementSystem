package club.xiaozhe.library.gui.pages;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.gui.components.BookFieldArrays;
import club.xiaozhe.library.gui.components.BookFieldSearchBox;
import club.xiaozhe.library.service.BooksService;

import javax.swing.*;
import java.sql.SQLException;

public class DeletePage extends JPanel {
    JComboBox<String> select;
    JTextField input;

    public DeletePage() {
        add(select = new BookFieldSearchBox());
        add(createInputArea());
        add(createSubmitButton());
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
            int row = BooksService.delete(BookFieldArrays.FIELDS[select.getSelectedIndex()], input.getText());
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
