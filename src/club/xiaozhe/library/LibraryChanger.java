package club.xiaozhe.library;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.exception.InvalidDateFormatException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 书籍“修改”门面，目前只有根据id修改的方法。
 * <p>负责所有 UPDATE 型 SQL 的拼写与执行，提供单字段/追加式两种语义：
 * <ul>
 *   <li>单字段：name、price、date … 直接覆盖</li>
 *   <li>追加式：addAuthor、addCategory 在原有值后追加逗号分隔内容</li>
 * </ul>
 * <p>异常策略：
 * <ul>
 *   <li>数据库失败 → SQLException 原样向上传递</li>
 *   <li>业务语义错误（日期格式、ID 不存在）→ 对应 BookBusinessException 抛出</li>
 * </ul>
 * <p>所有方法返回受影响行数；0 表示未找到或格式校验失败。
 */
class LibraryChanger {
    private final Connection connection;
    private final LibrarySelector selector;

    /**
     * 构造函数，为了实现追加功能需要或许selector对象
     *
     * @param connection 数据库的连接
     * @param selector   选择器
     */
    protected LibraryChanger(Connection connection, LibrarySelector selector) {
        this.connection = connection;
        this.selector = selector;
    }

    /**
     * 根据id改书名
     *
     * @param id   id
     * @param name 书名
     * @return 修改的行数（0或1）
     * @throws SQLException 数据库访问失败时抛出
     */
    public int changeNameById(String id, String name) throws SQLException {
        String sql = "UPDATE Books SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id改作者
     *
     * @param id      id
     * @param authors 作者
     * @return 修改的行数（0或1）
     * @throws SQLException 数据库访问失败时抛出
     */
    public int changeAuthorsById(String id, String authors) throws SQLException {
        String sql = "UPDATE Books SET authors = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, authors);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id添加作者
     *
     * @param id     id
     * @param author 作者
     * @return 修改的行数（0或1）
     * @throws SQLException          数据库访问失败时抛出
     * @throws BookBusinessException 发生业务逻辑错误时抛出
     */
    public int addAuthorById(String id, String author) throws SQLException, BookBusinessException {
        Book book = selector.selectBookById(id);
        return changeAuthorsById(id, book.getAuthorsStr() + "," + author);
    }

    /**
     * 根据id改出版社
     *
     * @param id        id
     * @param publisher 出版社
     * @return 修改的行数（0或1）
     * @throws SQLException 数据库访问失败时抛出
     */
    public int changePublisherById(String id, String publisher) throws SQLException {
        String sql = "UPDATE Books SET publisher = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, publisher);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id改时间
     *
     * @param id   id
     * @param date 出版时间
     * @return 修改的行数（0或1）
     * @throws SQLException          数据库访问失败时抛出
     * @throws BookBusinessException 发生业务逻辑错误时抛出
     */
    public int changeDateById(String id, String date) throws SQLException, BookBusinessException {
        if (!Utils.isTimeMatched(date))
            throw new InvalidDateFormatException(date);
        String sql = "UPDATE Books SET publicationDate = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, date);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id改价格
     *
     * @param id    id
     * @param price 价格
     * @return 修改的行数（0或1）
     * @throws SQLException 数据库访问失败时抛出
     */
    public int changePriceById(String id, BigDecimal price) throws SQLException {
        String sql = "UPDATE Books SET price = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, price);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id改分类
     *
     * @param id         id
     * @param categories 分类
     * @return 修改的行数（0或1）
     * @throws SQLException 数据库访问失败时抛出
     */
    public int changeCategoriesById(String id, String categories) throws SQLException {
        String sql = "UPDATE Books SET categories = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categories);
            ps.setString(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * 根据id添加分类
     *
     * @param id       id
     * @param category 分类
     * @return 修改的行数（0或1）
     * @throws SQLException          数据库访问失败时抛出
     * @throws BookBusinessException 发生业务逻辑错误时抛出
     */
    public int addCategoryById(String id, String category) throws SQLException, BookBusinessException {
        Book book = selector.selectBookById(id);
        return changeCategoriesById(id, book.getAuthorsStr() + "," + category);
    }
}
