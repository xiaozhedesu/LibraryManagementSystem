package club.xiaozhe.library.gui;

import club.xiaozhe.library.dao.JDBCActuator;
import club.xiaozhe.library.gui.pages.AddPage;
import club.xiaozhe.library.gui.pages.SearchPage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main extends JFrame {
    private static final Properties properties = new Properties();

    public Main() {
        init();
        addPages();
    }

    /**
     * 连接数据库的入口函数
     */
    private static void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 正常情况下应该不会触发
            throw new RuntimeException("JDBC不存在！", e);
        }

        readProperties();
        Connection connection = connect();
        JDBCActuator.setConnection(connection);
    }

    /**
     * 读取配置文件
     */
    private static void readProperties() {
        try (FileInputStream fis = new FileInputStream("profile.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对数据库进行连接
     *
     * @return 获取的Connection对象
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

    public static void main(String[] args) {
        connectDatabase();
        new Main().setVisible(true);
    }

    private void init() {
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("图书管理系统（GUI测试版）");
    }

    private void addPages() {
        JTabbedPane tabbedPane = new JTabbedPane();
        // TODO 添加页面
        tabbedPane.add(new SearchPage(), "查找");
        tabbedPane.add(new AddPage(), "添加");
        add(tabbedPane);
    }
}
