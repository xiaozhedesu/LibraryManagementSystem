package club.xiaozhe.library.support;

import club.xiaozhe.library.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从ResultSet对象提取出Book对象的类
 */
public class BookResultSetHandler implements ResultSetHandler<Book> {
    @Override
    public List<Book> handler(ResultSet set) throws SQLException {
        ArrayList<Book> result = new ArrayList<>();
        while (set.next()) {
            result.add(new Book(
                    set.getString("id"),
                    set.getString("name"),
                    set.getString("authors"),
                    set.getString("publisher"),
                    set.getString("publicationDate"),
                    set.getBigDecimal("price"),
                    set.getString("categories")
            ));
        }
        return result;
    }
}
