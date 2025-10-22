package club.xiaozhe.library.dao;

import club.xiaozhe.library.support.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用于执行sql语句的类，不可实例化
 */
public final class JDBCActuator {
    private static Connection connection;
    private JDBCActuator() {}

    /**
     * 设置数据库连接
     * @param connection 连接
     */
    public static void setConnection(Connection connection) {
        JDBCActuator.connection = connection;
    }

    /**
     * 当connection使用时为空则快速失败
     */
    private static void ensureConnection() {
        if(connection == null) throw new NullPointerException("connection of Mysql is not exist.");
    }

    /**
     * 处理查询语句的方法
     * @param sql sql语句
     * @param handler 处理ResultSet的对象
     * @param params 查询参数
     * @return 处理后的对象列表
     * @param <T> 结果元素类型
     * @throws SQLException 数据库查询出现异常时抛出
     */
    public static <T> List<T> query(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException{
        ensureConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet set = ps.executeQuery()) {
                return handler.handler(set);
            }
        }
    }

    /**
     * 处理更新语句的方法
     * @param sql sql语句
     * @param params 参数
     * @return 改变行数
     * @throws SQLException 数据库查询出现异常时抛出
     */
    public static int update(String sql, Object... params) throws SQLException{
        ensureConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        }
    }
}
