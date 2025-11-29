package club.xiaozhe.library.gui.pages;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.gui.components.InputLinePanel;
import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.service.BooksService;
import club.xiaozhe.library.support.Utils;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class AddPage extends JPanel {
    private final InputLinePanel name, authors, publisher, publisherDate, price, categories;

    public AddPage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // 数据输入行
        add(name = new InputLinePanel("书名"));
        add(authors = new InputLinePanel("作者"));
        add(publisher = new InputLinePanel("出版社"));
        add(publisherDate = new InputLinePanel("出版时间"));
        add(price = new InputLinePanel("价格"));
        add(categories = new InputLinePanel("分类"));
        // 提交按钮
        JButton submit = new JButton("提交");
        submit.addActionListener(e -> addBook());
        add(submit);
    }

    /**
     * 校验数据合法性
     *
     * @throws BookBusinessException 当数据不符要求时抛出
     */
    private void checkData() throws BookBusinessException {
        if (name.getText().isBlank())
            throw new BookBusinessException("书名不能为空！");
        if (authors.getText().isBlank())
            throw new BookBusinessException("作者不能为空！");
        if (publisher.getText().isBlank())
            throw new BookBusinessException("出版社不能为空！");
        if (publisherDate.getText().isBlank())
            throw new BookBusinessException("出版日期不能为空！");
        if (!Utils.isTimeMatched(publisherDate.getText()))
            throw new BookBusinessException("时间格式输入错误：只接受格式为YYYY-MM-DD的数据");
        if (!Utils.isNumStr(price.getText().trim()))
            throw new BookBusinessException("价格必须是数字！");
        if (categories.getText().isBlank())
            throw new BookBusinessException("分类不能为空！");
    }

    /**
     * 清空输入内容
     */
    private void clearInputArea() {
        name.setText("");
        authors.setText("");
        publisher.setText("");
        publisherDate.setText("");
        price.setText("");
        categories.setText("");
    }

    /**
     * 向数据库添加书籍
     */
    private void addBook() {
        try {
            checkData();
            String id = String.valueOf(name.getText().hashCode());
            Book book = new Book(
                    id,
                    name.getText(),
                    authors.getText(),
                    publisher.getText(),
                    publisherDate.getText(),
                    new BigDecimal(price.getText().trim()),
                    categories.getText()
            );
            int res = BooksService.add(book);
            if (res == 1) {
                JOptionPane.showMessageDialog(AddPage.this,
                        "添加成功！",
                        "提示", JOptionPane.INFORMATION_MESSAGE);
                clearInputArea();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(AddPage.this,
                    "数据库发生错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        } catch (BookBusinessException e) {
            JOptionPane.showMessageDialog(AddPage.this,
                    "输入错误：" + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
