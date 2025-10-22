package club.xiaozhe.library.cli;

import club.xiaozhe.library.dao.JDBCActuator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static OPHandler handler;
    private static final Properties properties = new Properties();

    public static void main(String[] args) {
        initializeSystem();

        run();
    }

    public static void run() {
        while (true) {
            System.out.print("admin> ");
            String op = scan.nextLine().toLowerCase().trim();
            handler.handler(op);
        }
    }

    public static void initializeSystem() {
        System.out.println("进入系统中...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 正常情况下应该不会触发
            throw new RuntimeException("JDBC不存在！", e);
        }

        readProperties();
        Connection connection = connect();
        JDBCActuator.setConnection(connection);
        System.out.println("数据库连接成功。");
        handler = new OPHandler(connection);
    }

    public static void readProperties() {
        System.out.println("读取配置文件中...");
        try (FileInputStream fis = new FileInputStream("profile.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("读取成功。");
    }

    public static Connection connect() {
        String url = (String) properties.get("database.url");
        String user = (String) properties.get("database.username");
        String psw = (String) properties.get("database.password");

        if (url == null || user == null || psw == null) {
            throw new RuntimeException("配置文件中缺少连接数据库相关的配置！");
        }

        System.out.println("正在连接数据库Library_Management_System...");
        try {
            return DriverManager.getConnection(url, user, psw);
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败！", e);
        }
    }
}

