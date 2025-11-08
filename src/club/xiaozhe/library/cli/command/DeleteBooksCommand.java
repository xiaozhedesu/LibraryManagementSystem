package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.service.BooksService;
import club.xiaozhe.library.support.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DeleteBooksCommand extends Command {
    private final Scanner scan;

    public DeleteBooksCommand(Scanner scan) {
        this.scan = scan;
    }

    @Override
    public void execute() {
        System.out.print("输入删除条件：");
        String op = scan.next();
        String param = scan.nextLine().trim();

        if ("help".equals(op)) {
            new ShowHelpMessageCommand("delete").execute();
            return;
        }

        List<Book> books;
        try {
            books = BooksService.search(op, param);
        } catch (SQLException e) {
            System.out.println("❌ 查询书籍发生错误：" + e.getMessage());
            return;
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
            return;
        }

        if (books.isEmpty()) {
            System.out.println("❌ 未找到要删除的元素。");
            return;
        }

        try {
            if (Utils.deleteDoubleCheck(books)) {
                int row = BooksService.delete(op, param);
                if (row != 0) System.out.println("✅ 删除成功，共 " + row + " 项。");
            } else {
                System.out.println("❌ 已取消删除。");
            }
        } catch (SQLException e) {
            System.out.println("❌ 删除书籍发生错误：" + e.getMessage());
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
