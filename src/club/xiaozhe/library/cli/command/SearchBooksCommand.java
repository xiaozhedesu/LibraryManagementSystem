package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.model.Book;
import club.xiaozhe.library.service.BooksService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 实现搜索功能
 */
public class SearchBooksCommand extends Command {
    private final Scanner scan;

    public SearchBooksCommand(Scanner scan) {
        this.scan = scan;
    }

    @Override
    public void execute() {
        System.out.print("输入搜索条件：");
        String op = scan.next();
        String param = scan.nextLine().trim();

        if ("help".equals(op)) {
            new ShowHelpMessageCommand("search").execute();
            return;
        }

        try {
            List<Book> books = BooksService.search(op, param);
            books.forEach(System.out::println);
            System.out.println("共" + books.size() + "项。");
        } catch (SQLException e) {
            System.out.println("❌ 查询书籍发生错误：" + e.getMessage());
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
