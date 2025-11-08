package club.xiaozhe.library.cli.command;

import java.sql.Connection;
import java.util.Scanner;

/**
 * 将指令转换成数据库操作的类，实际操作通过调用library对象实现。是Library包里唯一开放数据库操作的类。
 */
public class OPHandler {
    private static final Scanner scan = new Scanner(System.in);
    private final Connection connection;

    /**
     * 构造函数
     *
     * @param connection 数据库连接成功后传入对象内使用
     */
    public OPHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * 处理指令的入口函数
     *
     * @param op 指令
     */
    public void handler(String op) {
        Command cmd = switch (op) {                       // 1. 只负责“找对象”
            case "exit", "stop", "quit" -> new ExitCommand(connection);
            case "help" -> new ShowHelpMessageCommand();
            case "help change" -> new ShowHelpMessageCommand("change");
            case "help delete" -> new ShowHelpMessageCommand("delete");
            case "help search" -> new ShowHelpMessageCommand("search");
            case "add" -> new AddBookCommand();
            case "adds" -> new AddBooksCommand(scan);
            case "delete" -> new DeleteBooksCommand(scan);
            case "change" -> new ChangeBooksCommand(scan);
            case "search" -> new SearchBooksCommand(scan);
            case "size" -> new ShowSizeCommand();
            case "" -> null;            // 当用户直接回车的时候，不必打印错误信息
            default -> new UnsupportedCommand();
        };

        if (cmd != null) {            // 2. 统一执行
            cmd.execute();
        }
    }
}
