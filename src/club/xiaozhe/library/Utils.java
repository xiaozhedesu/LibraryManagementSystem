package club.xiaozhe.library;

import club.xiaozhe.library.exception.BookBusinessException;
import club.xiaozhe.library.exception.IllegalPriceException;
import club.xiaozhe.library.exception.InsufficientParameterException;
import club.xiaozhe.library.exception.InvalidPriceRangeException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class Utils {
    private static final Scanner scan = new Scanner(System.in);

    private Utils() {
    }

    /**
     * 判断字符串是否为 YYYY-MM-DD 的合法日期（简单正则，不考虑润月/大小月细节）
     *
     * @param date 日期字符串
     * @return 符合格式返回true
     */
    public static boolean isTimeMatched(String date) {
        return date != null && date.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
    }

    /**
     * 判断字符串是否为数字（整数或小数）
     *
     * @param numStr 要判断的字符串
     * @return 符合数字格式返回true
     */
    public static boolean isNumStr(String numStr) {
        return numStr != null && numStr.matches("\\d+(\\.\\d+)?");
    }

    /**
     * 判断BigDecimal对象是否为正数，写成一个函数式为了提高可读性
     *
     * @param num BigDecimal
     * @return 是正数则返回true
     */
    public static boolean isPositive(BigDecimal num) {
        return num.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 确认区间范围是否合法 [min,max]
     *
     * @param min 最小值
     * @param max 最大值
     * @return 当 min <= max 时返回1
     */
    public static boolean checkRange(BigDecimal min, BigDecimal max) {
        return max.compareTo(min) >= 0;
    }

    /**
     * 解析用户输入的价格区间
     *
     * @param val 参数字符串
     * @return 返回一个长度为2的BigDecimal数组，第一个为min，第二个为max
     */
    public static BigDecimal[] parsePriceRange(String val) throws BookBusinessException {
        String[] vals = val.split("\\s+", 2);
        if (vals.length < 2)
            throw new InsufficientParameterException("priceRange需要2个参数：priceRange <min> <max>");
        if (!isNumStr(vals[0]))
            throw new IllegalPriceException(vals[0]);
        if (!isNumStr(vals[1]))
            throw new IllegalPriceException(vals[1]);
        BigDecimal min = new BigDecimal(vals[0]);
        BigDecimal max = new BigDecimal(vals[1]);
        if (!checkRange(min, max))
            throw new InvalidPriceRangeException(min, max);
        return new BigDecimal[]{min, max};
    }

    /**
     * 将查找后的ResultSet对象转化为List返回，因为使用地点多于是封装一个函数
     *
     * @param set 执行SELECT语句后返回的ResultSet对象
     * @return 查找语句获取到的书籍信息列表
     */
    public static ArrayList<Book> booksSet2List(ResultSet set) throws SQLException {
        ArrayList<Book> result = new ArrayList<>();
        while (set.next()) {
            result.add(new Book(
                    set.getString("id"),
                    set.getString("name"),
                    set.getString("authors"),
                    set.getString("publisher"),
                    set.getString("publicationDate"),
                    set.getBigDecimal("price"),
                    set.getString("categories")
            ));
        }
        return result;
    }


    /**
     * 输入书名
     *
     * @return 书名
     */
    public static String scanName() {
        System.out.print("请输入书名：");
        return scan.nextLine().trim();
    }

    /**
     * 输入作者
     *
     * @return 作者
     */
    public static String scanAuthors() {
        System.out.print("请输入作者（如果有多个，中间用英文逗号分隔）：");
        return scan.nextLine().trim();
    }

    /**
     * 输入出版社
     *
     * @return 出版社
     */
    public static String scanPublisher() {
        System.out.print("请输入出版社：");
        return scan.nextLine().trim();
    }

    /**
     * 输入出版时间
     *
     * @return 格式错误时返回Optional.empty()，其余情况返回Optional String
     */
    public static Optional<String> scanPublicationDate() {
        System.out.print("请输入出版时间(YYYY-MM-DD)：");
        String publicationDate = scan.next();
        if (!isTimeMatched(publicationDate)) {
            System.out.println("❌ 时间格式输入错误！");
            return Optional.empty();
        }
        return Optional.of(publicationDate);
    }

    /**
     * 输入价格
     *
     * @return 价格，当价格输入错误时会返回0
     */
    public static BigDecimal scanPrice() {
        System.out.print("请输入价格：");
        try {
            BigDecimal price = scan.nextBigDecimal();
            scan.nextLine();
            return price;
        } catch (InputMismatchException e) {
            System.out.println("❌ 价格输入错误！");
            scan.nextLine();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 输入分类
     *
     * @return 分类
     */
    public static String scanCategory() {
        System.out.print("请输入分类（如果有多个，中间用英文逗号分隔）：");
        return scan.nextLine().trim();
    }

    /**
     * 提供给添加函数使用的二次确认函数
     *
     * @param book 确认添加书籍的信息
     * @return 可添加为true，否则为false
     */
    public static boolean addDoubleCheck(Book book) {
        System.out.print("书籍信息：\n" + book + "\n是否添加书籍(y/n)？");
        String check = scan.next().trim().toLowerCase();
        scan.nextLine();
        return "y".equals(check);
    }

    /**
     * 对删除进行二次确认
     *
     * @param books 确认删除书籍的信息 List
     * @return 可删除为true，否则为false
     */
    public static boolean deleteDoubleCheck(List<Book> books) {
        System.out.println("即将删除书籍：");
        books.forEach(System.out::println);
        System.out.print("请二次确认删除(y,n)：");
        String check = scan.next().trim().toLowerCase();
        scan.nextLine();
        return "y".equals(check);
    }
}
