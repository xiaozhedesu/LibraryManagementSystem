package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.dao.JDBCActuator;

import java.sql.SQLException;
import java.util.List;

/**
 * 展示数据表长度
 */
public class ShowSizeCommand extends Command{
    @Override
    public void execute() {
        try {
            long size = JDBCActuator.query("SELECT COUNT(*) FROM books", (set) -> {
                set.next();
                return List.of(set.getLong(1));
            }).get(0);
            System.out.println("Books中共有 " + size + " 项。");
        } catch (SQLException e) {
            System.out.println("❌ 长度获取失败：" + e.getMessage());
        }
    }
}
