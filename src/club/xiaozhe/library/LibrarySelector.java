package club.xiaozhe.library;

import club.xiaozhe.library.exception.BookNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 书籍“查询”门面。
 * <p>负责所有 SELECT 型 SQL 的拼写与执行，提供：
 * <ul>
 *   <li>单主键精确查询（{@code selectBookById}）</li>
 *   <li>多字段模糊查询（name、author、publisher、category …）</li>
 *   <li>数值区间查询（price BETWEEN …）</li>
 *   <li>全表列表（{@code selectBooks}）</li>
 * </ul>
 * <p>异常策略：
 * <ul>
 *   <li>数据库失败 → SQLException 原样向上传递</li>
 *   <li>单主键查询无结果 → BookNotFoundException</li>
 *   <li>模糊/区间查询无结果 → 返回空 List（不抛异常）</li>
 * </ul>
 * <p>所有查询方法返回 List 或 Book；空结果代表“未找到”。
 */
class LibrarySelector {
    private final Connection connection;

    /**
     * 构造函数
     *
     * @param connection 数据库连接成功后传入对象内使用
     */
    protected LibrarySelector(Connection connection) {
        this.connection = connection;
    }

    /**
     * 查询所有书籍
     *
     * @return List
     */
    public List<Book> selectBooks() throws SQLException {
        String sql = "SELECT * FROM Books";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet set = ps.executeQuery()) {
            return Utils.booksSet2List(set);
        }
    }

    /**
     * 按id查询书籍
     *
     * @param id 主键
     * @return 单本书
     */
    public Book selectBookById(String id) throws SQLException, BookNotFoundException {
        String sql = "SELECT * FROM Books WHERE id = ? ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet set = ps.executeQuery()) {
                List<Book> res = Utils.booksSet2List(set);
                if (res.isEmpty())
                    throw new BookNotFoundException(id);
                return res.get(0);
            }
        }
    }

    /**
     * 按书名查询书籍（模糊匹配）
     *
     * @param name 书名
     * @return List
     */
    public List<Book> selectBooksByName(String name) throws SQLException {
        String sql = "SELECT * FROM Books WHERE name LIKE ? ORDER BY name";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet set = ps.executeQuery()) {
                return Utils.booksSet2List(set);
            }
        }
    }

    /**
     * 按作者查询书籍（模糊匹配）
     *
     * @param author 作者
     * @return List
     */
    public List<Book> selectBooksByAuthor(String author) throws SQLException {
        String sql = "SELECT * FROM Books WHERE authors LIKE ? ORDER BY authors";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + author + "%");
            try (ResultSet set = ps.executeQuery()) {
                return Utils.booksSet2List(set);
            }
        }
    }

    /**
     * 按出版社查询书籍（模糊匹配）
     *
     * @param publisher 出版社
     * @return List
     */
    public List<Book> selectBooksByPublisher(String publisher) throws SQLException {
        String sql = "SELECT * FROM Books WHERE publisher LIKE ? ORDER BY publisher";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + publisher + "%");
            try (ResultSet set = ps.executeQuery()) {
                return Utils.booksSet2List(set);
            }
        }
    }

    /**
     * 按价格区间查找书籍（模糊匹配）
     *
     * @param min 最小值
     * @param max 最大值
     * @return List
     */
    public List<Book> selectBooksByPriceRange(BigDecimal min, BigDecimal max) throws SQLException {
        String sql = "SELECT * FROM Books WHERE price BETWEEN ? AND ? ORDER BY price";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, min);
            ps.setBigDecimal(2, max);
            try (ResultSet set = ps.executeQuery()) {
                return Utils.booksSet2List(set);
            }
        }
    }

    /**
     * 按分类查找书籍（模糊匹配）
     *
     * @param category 分类
     * @return List
     */
    public List<Book> selectBooksByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM Books WHERE categories LIKE ? ORDER BY categories";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + category + "%");
            try (ResultSet set = ps.executeQuery()) {
                return Utils.booksSet2List(set);
            }
        }
    }
}
