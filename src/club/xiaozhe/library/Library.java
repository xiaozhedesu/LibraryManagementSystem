package club.xiaozhe.library;

import club.xiaozhe.library.exception.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 书籍管理库的门面类，封装了对数据库的CRUD操作。
 * <p>通过内部的DAO类（LibrarySelector、LibraryChanger、LibraryDeleter）实现对书籍信息的查询、修改、添加和删除功能。
 * <p>设计上遵循单一职责原则，每个内部类负责一块具体的业务逻辑。
 * <p>提供了一系列的公共方法供外部调用，包括搜索、修改、添加和删除书籍。
 * <p>所有数据库操作均通过PreparedStatement执行，以防止SQL注入。
 * <p>异常处理策略：数据库异常向外抛出SQLException，业务逻辑异常抛出BookBusinessException。
 */
class Library {
    /**
     * 与数据库的连接
     */
    private final Connection connection;
    /**
     * 掌管选择方法的对象
     */
    private final LibrarySelector selector;
    /**
     * 掌管修改方法的对象
     */
    private final LibraryChanger changer;
    /**
     * 掌管删除方法的对象
     */
    private final LibraryDeleter deleter;

    /**
     * 构造函数
     *
     * @param connection 数据库连接成功后传入对象内使用
     */
    public Library(Connection connection) {
        this.connection = connection;
        this.selector = new LibrarySelector(connection);
        this.changer = new LibraryChanger(connection, selector);
        this.deleter = new LibraryDeleter(connection);
    }

    /**
     * 在数据库中获取Books表的表长
     *
     * @return the size of Books
     */
    public long booksSize() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Books");
             ResultSet set = ps.executeQuery()) {
            set.next();
            return set.getLong(1);
        }
    }

    /**
     * 返回值为列表的查找功能
     *
     * @param op  指令
     * @param val 值
     * @return 查询到的书籍列表
     */
    public List<Book> search(String op, String val)
            throws SQLException, BookBusinessException {
        return switch (op) {
            case "all" -> selector.selectBooks();
            case "id" -> List.of(selector.selectBookById(val));
            case "name" -> selector.selectBooksByName(val);
            case "author" -> selector.selectBooksByAuthor(val);
            case "publisher" -> selector.selectBooksByPublisher(val);
            case "category" -> selector.selectBooksByCategory(val);
            case "priceRange" -> {
                BigDecimal[] range = Utils.parsePriceRange(val);
                yield selector.selectBooksByPriceRange(range[0], range[1]);
            }
            default -> throw new BookBusinessException("不支持的操作：" + op);
        };
    }

    /**
     * 根据id实现修改语句的函数
     *
     * @param op  指令
     * @param id  id
     * @param val 值
     * @return 修改的行数
     * @throws SQLException          数据库访问失败时抛出
     * @throws BookBusinessException 发生业务逻辑错误时抛出
     */
    public int changeById(String op, String id, String val)
            throws SQLException, BookBusinessException {
        return switch (op) {
            case "name" -> changer.changeNameById(id, val);
            case "authors" -> changer.changeAuthorsById(id, val);
            case "addAuthor" -> changer.addAuthorById(id, val);
            case "publisher" -> changer.changePublisherById(id, val);
            case "date" -> changer.changeDateById(id, val);
            case "price" -> {
                if (!Utils.isNumStr(val))
                    throw new IllegalPriceException(val);
                BigDecimal price = new BigDecimal(val);
                yield changer.changePriceById(id, price);
            }
            case "categories" -> changer.changeCategoriesById(id, val);
            case "addCategory" -> changer.addCategoryById(id, val);
            default -> throw new BookBusinessException("不支持的操作：" + op);
        };
    }

    /**
     * 实现添加语句的函数
     *
     * @param book 封装好的Book对象
     * @return 成功添加返回1，未成功会返回0
     * @throws SQLException 数据库访问失败时抛出
     */
    public int add(Book book) throws SQLException {
        String sql = "INSERT INTO Books(id, name, authors, publisher, publicationDate, price, categories) VALUES(?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getId());
            ps.setString(2, book.getName());
            ps.setString(3, book.getAuthorsStr());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getPublicationDate());
            ps.setBigDecimal(6, book.getPrice());
            ps.setString(7, book.getCategoriesStr());

            return ps.executeUpdate();
        }
    }

    /**
     * 实现删除语句的函数
     *
     * @param op  指令
     * @param val 值
     * @return 删除的行数
     * @throws SQLException 数据库访问失败时抛出
     */
    public int delete(String op, String val) throws SQLException, BookBusinessException {
        return switch (op) {
            case "id" -> deleter.deleteBookById(val);
            case "name" -> deleter.deleteBooksByName(val);
            case "author" -> deleter.deleteBooksByAuthor(val);
            case "publisher" -> deleter.deleteBooksByPublisher(val);
            case "category" -> deleter.deleteBooksByCategory(val);
            case "priceRange" -> {
                BigDecimal[] range = Utils.parsePriceRange(val);
                yield deleter.deleteBooksByPriceRange(range[0], range[1]);
            }
            default -> throw new BookBusinessException("不支持的操作：" + op);
        };
    }
}
