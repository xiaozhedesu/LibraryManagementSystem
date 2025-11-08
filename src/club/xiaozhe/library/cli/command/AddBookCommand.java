package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.service.BooksService;
import club.xiaozhe.library.support.Utils;

import java.sql.SQLException;

/**
 * 实现添加书本功能。
 */
public class AddBookCommand extends Command{
    @Override
    public void execute() {
        Book book = Utils.scanBook();
        try {
            if (Utils.addDoubleCheck(book)) {
                int row = BooksService.add(book);
                System.out.println(row == 1 ? "✅ 添加成功。" : "❌ 添加失败。");
            } else {
                System.out.println("❌ 已取消添加。");
            }
        } catch (SQLException e) {
            System.out.println("❌ 添加书籍发生错误：" + e.getMessage());
        }
    }
}
