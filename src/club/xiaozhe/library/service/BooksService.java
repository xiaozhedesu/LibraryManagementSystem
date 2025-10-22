package club.xiaozhe.library.service;

import club.xiaozhe.library.dao.JDBCActuator;
import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.strategy.book.SQLStrategyEnum;
import club.xiaozhe.library.support.BookResultSetHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 掌管Books表CRUD的类
 */
public class BooksService {
    private BooksService() {
    }

    /**
     * 查询函数
     *
     * @param op    指令
     * @param param 参数
     * @return 查询拿到的Book列表
     * @throws SQLException          数据库查询发生错误时抛出
     * @throws BookBusinessException 业务逻辑出现错误时抛出
     */
    public static List<Book> search(String op, String param)
            throws SQLException, BookBusinessException {
        try {
            SQLStrategyEnum query = SQLStrategyEnum.ofSearch(op);
            return JDBCActuator.query(query.sql(), new BookResultSetHandler(), query.param(param));
        } catch (IllegalArgumentException e) {
            throw new BookBusinessException("不支持的操作：" + op);
        }
    }

    /**
     * 删除函数
     *
     * @param op    指令
     * @param param 参数
     * @return 被改变的行数
     * @throws SQLException          数据库查询发生错误时抛出
     * @throws BookBusinessException 业务逻辑出现错误时抛出
     */
    public static int delete(String op, String param)
            throws SQLException, BookBusinessException {
        try {
            SQLStrategyEnum update = SQLStrategyEnum.ofDelete(op);
            return JDBCActuator.update(update.sql(), update.param(param));
        } catch (IllegalArgumentException e) {
            throw new BookBusinessException("不支持的操作：" + op);
        }
    }

    /**
     * 添加函数
     *
     * @param book 打包好的Book对象
     * @return 添加的行数（成功为1，失败为0）
     * @throws SQLException 数据库查询发生错误时抛出
     */
    public static int add(Book book) throws SQLException {
        SQLStrategyEnum update = SQLStrategyEnum.ofAdd("all");
        return JDBCActuator.update(update.sql(), book.toParams());
    }

    /**
     * 修改函数
     *
     * @param op    指令
     * @param param 参数
     * @return 被改变的行数
     * @throws SQLException          数据库查询发生错误时抛出
     * @throws BookBusinessException 业务逻辑出现错误时抛出
     */
    public static int change(String op, String param)
            throws SQLException, BookBusinessException {
        try {
            SQLStrategyEnum update = SQLStrategyEnum.ofChange(op);
            return JDBCActuator.update(update.sql(), update.param(param));
        } catch (IllegalArgumentException e) {
            throw new BookBusinessException("不支持的操作：" + op);
        }
    }
}
