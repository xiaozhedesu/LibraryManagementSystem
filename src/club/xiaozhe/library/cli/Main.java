package club.xiaozhe.library.cli;

import club.xiaozhe.library.cli.command.OPHandler;
import club.xiaozhe.library.support.ConnectDatabase;

import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static OPHandler handler;

    public static void main(String[] args) {
        // 连接数据库
        System.out.println("进入系统中...");
        handler = new OPHandler(ConnectDatabase.execute());
        System.out.println("数据库连接成功。");
        // 运行死循环
        run();
    }

    public static void run() {
        while (true) {
            System.out.print("admin> ");
            String op = scan.nextLine().toLowerCase().trim();
            handler.handler(op);
        }
    }
}

