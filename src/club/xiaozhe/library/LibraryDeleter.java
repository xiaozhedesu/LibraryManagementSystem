package club.xiaozhe.library;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 书籍“删除”门面。
 * <p>负责所有 DELETE 型 SQL 的拼写与执行，支持：
 * <ul>
 *   <li>单主键精确删除（{@code deleteBookById}）</li>
 *   <li>多字段模糊批量删除（name、author、publisher、category …）</li>
 *   <li>数值区间批量删除（price BETWEEN …）</li>
 * </ul>
 * <p>异常策略：
 * <ul>
 *   <li>数据库失败 → SQLException 原样向上传递</li>
 *   <li>所有方法返回受影响行数；0 表示未找到任何匹配记录</li>
 * </ul>
 * <p>所有 SQL 均采用 **模糊匹配（LIKE '%xxx%'）** 或 **区间（BETWEEN）**，删除前不会先查询。
 */
class LibraryDeleter {
    private final Connection connection;

    /**
     * 构造函数
     *
     * @param connection 数据库连接成功后传入对象内使用
     */
    protected LibraryDeleter(Connection connection) {
        this.connection = connection;
    }

    /**
     * 按id删除书籍
     *
     * @param id id
     * @return 删除成功返回1，失败为0。
     */
    public int deleteBookById(String id) throws SQLException {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 按书名删除书籍（模糊匹配）
     *
     * @param name 书名
     * @return 失败返回0，成功返回大于0的整数
     */
    public int deleteBooksByName(String name) throws SQLException {
        String sql = "DELETE FROM Books WHERE name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            return ps.executeUpdate();
        }
    }

    /**
     * 按作者名删除书籍（模糊匹配）
     *
     * @param author 作者名
     * @return 失败返回0，成功返回大于0的整数
     */
    public int deleteBooksByAuthor(String author) throws SQLException {
        String sql = "DELETE FROM Books WHERE authors LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + author + "%");
            return ps.executeUpdate();
        }
    }

    /**
     * 按出版社删除书籍（模糊匹配）
     *
     * @param publisher 出版社
     * @return 失败返回0，成功返回大于0的整数
     */
    public int deleteBooksByPublisher(String publisher) throws SQLException {
        String sql = "DELETE FROM Books WHERE publisher LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + publisher + "%");
            return ps.executeUpdate();
        }
    }

    /**
     * 按价格区间删除书籍
     *
     * @param min 价格下限（包含）
     * @param max 价格上限（包含）
     * @return 失败返回0，成功返回大于0的整数
     */
    public int deleteBooksByPriceRange(BigDecimal min, BigDecimal max) throws SQLException {
        String sql = "DELETE FROM Books WHERE price BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, min);
            ps.setBigDecimal(2, max);
            return ps.executeUpdate();
        }
    }

    /**
     * 按分类删除书籍（批量）
     *
     * @param category 分类
     * @return 失败返回0，成功返回大于0的整数
     */
    public int deleteBooksByCategory(String category) throws SQLException {
        String sql = "DELETE FROM Books WHERE categories LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + category + "%");
            return ps.executeUpdate();
        }
    }
}
