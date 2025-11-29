package club.xiaozhe.library.gui.pages;

import club.xiaozhe.library.gui.components.BooksTable;
import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.service.BooksService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 搜索页面
 */
public class SearchPage extends JPanel {
    public static final String[] SHOW_MODES = {
            "所有", "id", "书名", "作者", "出版社", "分类"
    };
    public static final String[] FIELDS = {
            "all", "id", "name", "author", "publisher", "category"
    };
    /**
     * 查找模式选择框
     */
    private JComboBox<String> select;
    /**
     * 输入框，获取用户输入
     */
    private JTextField input;
    /**
     * 显示的表格对象，可以对它动态修改数据
     */
    private JTable table;

    public SearchPage() {
        setLayout(new BorderLayout());
        addSelectPanel();
        addDataTable();
    }

    /**
     * 添加搜索部分的控件
     */
    private void addSelectPanel() {
        JPanel panel = new JPanel();
        panel.add(createSelectBox());
        panel.add(createInputArea());
        panel.add(createSubmitButton());
        add(panel, BorderLayout.NORTH);
    }

    /**
     * 选择框
     */
    private JComboBox<String> createSelectBox() {
        select = new JComboBox<>();
        select.setModel(new DefaultComboBoxModel<>(SHOW_MODES));
        return select;
    }


    /**
     * 输入控件
     */
    private JTextField createInputArea() {
        input = new JTextField(30);
        input.addActionListener(e -> updateTable(FIELDS[select.getSelectedIndex()], input.getText()));
        return input;
    }

    /**
     * 查询按钮控件
     */
    private JButton createSubmitButton() {
        JButton submit = new JButton("查询");
        submit.addActionListener(e -> updateTable(FIELDS[select.getSelectedIndex()], input.getText()));
        return submit;
    }

    /**
     * 数据显示表格控件
     */
    private void addDataTable() {
        table = new JTable(new BooksTable(List.of()));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updateTable("all", "");
    }

    /**
     * 向数据库中获取数据后更新table
     */
    private void updateTable(String op, String param) {
        new SwingWorker<List<Book>, Void>() {
            @Override
            protected List<Book> doInBackground() throws Exception {
                return BooksService.search(op, param);
            }

            @Override
            protected void done() {
                try {
                    List<Book> data = get();
                    table.setModel(new BooksTable(data));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(SearchPage.this,
                            "查询失败：" + e.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                    table.setModel(new BooksTable(List.of()));
                }
            }
        }.execute();
    }
}
