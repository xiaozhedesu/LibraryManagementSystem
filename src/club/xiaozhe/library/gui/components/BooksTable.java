package club.xiaozhe.library.gui.components;

import club.xiaozhe.library.model.Book;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 重写TableModel以提供对<code>List&lt;book&gt;</code>数据的支持
 */
public class BooksTable extends AbstractTableModel {
    List<Book> list;
    String[] header = {"id", "书名", "作者", "出版社", "出版日期", "价格", "分类"};

    public BooksTable(List<Book> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = list.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> book.getId();
            case 1 -> book.getName();
            case 2 -> book.getAuthorsStr();
            case 3 -> book.getPublisher();
            case 4 -> book.getPublicationDate();
            case 5 -> book.getPrice();
            case 6 -> book.getCategoriesStr();
            default -> throw new IndexOutOfBoundsException("表格下标越界！");      // 如果报这个错说明代码写错了
        };
    }
}
