package club.xiaozhe.library.support;

import club.xiaozhe.library.dao.JDBCActuator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 连接数据库（本项目使用的是mysql，如果需要扩展可以改造成抽象工厂）
 */
public class ConnectDatabase {
    private static final Properties properties = new Properties();

    public static Connection execute() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 正常情况下应该不会触发
            throw new RuntimeException("JDBC不存在！", e);
        }

        readProperties();
        Connection connection = connect();
        JDBCActuator.setConnection(connection);
        return connection;
    }

    /**
     * 读取本地配置
     */
    private static void readProperties() {
        try (FileInputStream fis = new FileInputStream("profile.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到连连接数据库所需数据后进行连接
     * @return 连接后的Connection对象
     */
    private static Connection connect() {
        String url = (String) properties.get("database.url");
        String user = (String) properties.get("database.username");
        String psw = (String) properties.get("database.password");

        if (url == null || user == null || psw == null) {
            throw new RuntimeException("配置文件中缺少连接数据库相关的配置！");
        }

        try {
            return DriverManager.getConnection(url, user, psw);
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败！", e);
        }
    }
}
