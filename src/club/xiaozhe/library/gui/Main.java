package club.xiaozhe.library.gui;

import club.xiaozhe.library.gui.pages.*;
import club.xiaozhe.library.support.ConnectDatabase;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends JFrame {
    private final Connection connection;

    public Main() {
        // 连接数据库
        connection = ConnectDatabase.execute();
        init();
        addPages();
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    private void init() {
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });
        setTitle("图书管理系统（GUI测试版）");
    }

    private void addPages() {
        JTabbedPane tabbedPane = new JTabbedPane();
        // TODO 添加页面
        tabbedPane.add(new SearchPage(), "查找");
        tabbedPane.add(new AddPage(), "添加");
        tabbedPane.add(new ChangePage(), "修改");
        tabbedPane.add(new DeletePage(), "删除");
        add(tabbedPane);
    }
}
