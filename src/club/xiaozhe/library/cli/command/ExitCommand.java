package club.xiaozhe.library.cli.command;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 退出时调用函数，用于关闭一些流。
 */
public class ExitCommand extends Command {
    private final Connection connection;

    public ExitCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void execute() {
        System.out.println("正在退出系统...");
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("❌ 连接关闭错误：" + e.getMessage());
        }
        System.exit(0);
    }

}
