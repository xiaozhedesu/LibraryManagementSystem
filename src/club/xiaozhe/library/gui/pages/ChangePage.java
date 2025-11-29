package club.xiaozhe.library.gui.pages;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.gui.components.InputLinePanel;
import club.xiaozhe.library.service.BooksService;

import javax.swing.*;
import java.sql.SQLException;

public class ChangePage extends JPanel {
    public static final String[] SHOW_MODES = {
            "书名", "作者", "出版社", "出版时间", "价格", "分类"
    };
    public static final String[] FIELDS = {
            "name", "author", "publisher", "date", "price", "categories"
    };
    InputLinePanel id;
    JComboBox<String> select;
    JTextField change;
    JButton submit;

    public ChangePage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 第一行
        add(new JLabel("目前只提供根据书籍id修改特定字段的功能。"));

        add(id = new InputLinePanel("id: "));

        JPanel panel = new JPanel();
        panel.add(createSelectBox());
        panel.add(change = new JTextField(30));
        add(panel);

        submit = new JButton("修改");
        submit.addActionListener(e -> changeBook());
        add(submit);
    }

    private JComboBox<String> createSelectBox() {
        select = new JComboBox<>();
        select.setModel(new DefaultComboBoxModel<>(SHOW_MODES));
        return select;
    }

    private boolean doubleCheck() {
        int result = JOptionPane.showConfirmDialog(
                ChangePage.this,
                "在修改前请确保查询过修改的条目，并确保无输入错误，确认无误后请按是。",
                "确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.OK_OPTION;
    }

    private void clearInputArea() {
        id.setText("");
        change.setText("");
    }

    private void changeBook() {
        if ("".equals(id.getText()) || "".equals(change.getText()) || !doubleCheck())
            return;

        try {
            int row = BooksService.change(FIELDS[select.getSelectedIndex()], change.getText() + " " + id.getText());
            JOptionPane.showMessageDialog(ChangePage.this,
                    row == 1 ? "修改成功。" : "修改失败。",
                    "提示", JOptionPane.INFORMATION_MESSAGE);
            clearInputArea();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ChangePage.this,
                    "数据库发生错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        } catch (BookBusinessException e) {
            JOptionPane.showMessageDialog(ChangePage.this,
                    "发生错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
