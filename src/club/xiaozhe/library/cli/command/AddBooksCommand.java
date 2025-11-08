package club.xiaozhe.library.cli.command;

import java.util.Scanner;

/**
 * 添加多个书籍指令
 */
public class AddBooksCommand extends Command {
    private final Scanner scan;

    public AddBooksCommand(Scanner scan) {
        this.scan = scan;
    }

    @Override
    public void execute() {
        while (true) {
            new AddBookCommand().execute();
            System.out.print("还需要继续添加吗？(Y/n)：");
            String check = scan.next().trim().toLowerCase();
            scan.nextLine();
            if ("n".equals(check)) {
                return;
            }
        }
    }
}
