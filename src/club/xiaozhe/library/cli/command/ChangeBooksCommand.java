package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.service.BooksService;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * 实现修改书籍功能
 */
public class ChangeBooksCommand extends Command{
    private final Scanner scan;

    public ChangeBooksCommand(Scanner scan) {
        this.scan = scan;
    }

    @Override
    public void execute() {
        System.out.print("输入更改指令：");
        String op = scan.next();
        String param = scan.nextLine().trim();

        if ("help".equals(op)) {
            new ShowHelpMessageCommand("change").execute();
            return;
        }

        try {
            int row = BooksService.change(op,param);
            System.out.println("改变了 " + row + " 行。");
        } catch (SQLException e) {
            System.out.println("❌ 修改书籍发生错误：" + e.getMessage());
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
