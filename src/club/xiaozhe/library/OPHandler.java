package club.xiaozhe.library;

import club.xiaozhe.library.exception.BookBusinessException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * 将指令转换成数据库操作的类，实际操作通过调用library对象实现。是Library包里唯一开放数据库操作的类。
 */
public class OPHandler {
    private static final Scanner scan = new Scanner(System.in);
    private final Connection connection;
    private final Library library;

    /**
     * 构造函数
     *
     * @param connection 数据库连接成功后传入对象内使用
     */
    public OPHandler(Connection connection) {
        this.connection = connection;
        this.library = new Library(connection);
    }

    /**
     * 处理指令的入口函数
     *
     * @param op 指令
     */
    public void handler(String op) {
        switch (op) {
            case "exit", "stop", "quit" -> exit();
            case "help" -> System.out.println(HelpMessage.HELP);
            case "help change" -> System.out.println(HelpMessage.HELP_CHANGE);
            case "help delete" -> System.out.println(HelpMessage.HELP_DELETE);
            case "help search" -> System.out.println(HelpMessage.HELP_SEARCH);
            case "add" -> add();
            case "delete" -> delete();
            case "change" -> change();
            case "search" -> search();
            case "size" -> showSize();
            case "" -> {
            }   // 当用户直接回车的时候，不必打印错误信息
            default -> System.out.println("❌ 不支持的指令，请使用 help 查询指令。");
        }
    }

    /**
     * 退出时调用函数，用于关闭一些流。
     */
    private void exit() {
        System.out.println("正在退出系统...");
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("❌ 连接关闭错误：" + e.getMessage());
        }
        System.exit(0);
    }

    /**
     * 展示数据表长度
     */
    private void showSize() {
        try {
            long size = library.booksSize();
            System.out.println("Books中共有 " + size + " 项。");
        } catch (SQLException e) {
            System.out.println("长度获取失败：" + e.getMessage());
        }
    }

    /**
     * search指令：实现搜索功能。
     */
    private void search() {
        System.out.print("输入搜索条件：");
        String[] op = scan.nextLine().trim().split("\\s+", 2);

        if ("help".equals(op[0])) {
            System.out.println(HelpMessage.HELP_SEARCH);
            return;
        }

        List<Book> books;
        try {
            if ("all".equals(op[0])) {
                books = library.search(op[0], "");
            } else if (op.length >= 2) {
                books = library.search(op[0], op[1]);
            } else {
                System.out.println("❌ 参数不足！");
                return;
            }

            books.forEach(System.out::println);
            System.out.println("共" + books.size() + "项。");
        } catch (SQLException e) {
            System.out.println("❌ 查询书籍发生错误：" + e.getMessage());
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    /**
     * change指令：实现修改功能。
     */
    private void change() {
        System.out.print("输入更改指令：");
        String[] op = scan.nextLine().trim().split("\\s+", 3);

        if ("help".equals(op[0])) {
            System.out.println(HelpMessage.HELP_CHANGE);
            return;
        }

        if (op.length < 3) {
            System.out.println("❌ 参数不足！");
            return;
        }

        try {
            int row = library.changeById(op[0], op[1], op[2]);
            System.out.println("改变了 " + row + " 行。");
        } catch (SQLException e) {
            System.out.println("修改书籍发生错误：" + e.getMessage());
        } catch (BookBusinessException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    /**
     * add指令 ：实现添加功能。
     */
    private void add() {
        Book book = scanBook();
        try {
            if (Utils.addDoubleCheck(book)) {
                int row = library.add(book);
                System.out.println(row == 1 ? "✅ 添加成功。" : "❌ 添加失败。");
            } else {
                System.out.println("❌ 已取消添加。");
            }
        } catch (SQLException e) {
            System.out.println("❌ 添加书籍发生错误：" + e.getMessage());
        }
    }

    /**
     * 添加书籍时控制输入的代码
     *
     * @return 将用户输入的数据打包的Book
     */
    private Book scanBook() {
        String name = Utils.scanName();
        String id = String.valueOf(name.hashCode());
        String authors = Utils.scanAuthors();
        String publisher = Utils.scanPublisher();
        Optional<String> data;
        do {
            data = Utils.scanPublicationDate();
        } while (data.isEmpty());
        String publicationDate = data.get();
        BigDecimal price = Utils.scanPrice();
        while (!Utils.isPositive(price)) {
            System.out.println("❌ 价格需为正整数！");
            price = Utils.scanPrice();
        }
        String categories = Utils.scanCategory();

        return new Book(id, name, authors, publisher, publicationDate, price, categories);
    }

    /**
     * delete指令：实现删除功能。
     */
    private void delete() {
        System.out.print("输入删除条件：");
        String[] op = scan.nextLine().trim().split("\\s+", 2);

        if ("help".equals(op[0])) {
            System.out.println(HelpMessage.HELP_DELETE);
            return;
        }

        if (op.length < 2) {
            System.out.println("❌ 参数不足！");
            return;
        }

        List<Book> books;
        try {
            books = library.search(op[0], op[1]);
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
                int row = library.delete(op[0], op[1]);
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
