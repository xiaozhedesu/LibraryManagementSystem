package club.xiaozhe.library;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * <p>CREATE TABLE Books (
 * <p>    id              VARCHAR(32)  PRIMARY KEY,
 * <p>    name            VARCHAR(255) NOT NULL,
 * <p>    authors         VARCHAR(255) NOT NULL,
 * <p>    publisher       VARCHAR(255) NOT NULL,
 * <p>    publicationDate VARCHAR(255) NOT NULL,
 * <p>    price           DECIMAL(10,2) NOT NULL,
 * <p>    categories      VARCHAR(255) NOT NULL
 * <p>);
*/
public class Book {
    private String id;              // 书籍在数据库中的id
    private String name;            // 书籍名
    private String[] authors;       // 作者名
    private String publisher;       // 出版社名
    private String publicationDate;  // 出版日期
    private BigDecimal price;           // 价格
    private String[] categories;      // 分类

    /**
     * 缺少String[]的构造函数，单独处理数组，其他数据直接赋值。
     *
     * @param id              书籍在数据库中的唯一编号
     * @param name            书籍名称
     * @param publisher       出版社名称
     * @param publicationDate 出版日期，建议格式 yyyy-MM-dd
     * @param price           定价（人民币）
     */
    private Book(String id,
                 String name,
                 String publisher,
                 String publicationDate,
                 BigDecimal price) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    /**
     * 全参构造函数
     *
     * @param id              书籍在数据库中的唯一编号
     * @param name            书籍名称
     * @param authors         作者数组
     * @param publisher       出版社名称
     * @param publicationDate 出版日期，建议格式 yyyy-MM-dd
     * @param price           定价（人民币）
     * @param category        分类数组
     */
    public Book(String id,
                String name,
                String[] authors,
                String publisher,
                String publicationDate,
                BigDecimal price,
                String[] category) {
        this(id, name, publisher, publicationDate, price);
        this.authors = authors;
        this.categories = category;
    }

    /**
     * 便捷构造：authors 与 category 传入逗号分隔的单个字符串，
     * 内部自动 split 成数组。
     *
     * @param id              书籍在数据库中的唯一编号
     * @param name            书籍名称
     * @param authorsStr      作者列表，用英文逗号分隔，可为空
     * @param publisher       出版社名称
     * @param publicationDate 出版日期，建议格式 yyyy-MM-dd
     * @param price           定价（人民币）
     * @param categoriesStr   分类列表，用英文逗号分隔，可为空
     */
    public Book(String id,
                String name,
                String authorsStr,
                String publisher,
                String publicationDate,
                BigDecimal price,
                String categoriesStr) {
        this(id, name, publisher, publicationDate, price);
        setAuthors(authorsStr);
        setCategories(categoriesStr);
    }

    /* Getter & Setter */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public void setAuthors(String authorsStr) {
        this.authors = (authorsStr == null || authorsStr.isEmpty()) ? new String[0] : authorsStr.split("\\s*,\\s*");
    }

    public String getAuthorsStr() {
        return String.join(",", authors);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = new BigDecimal(String.valueOf(price));
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setCategories(String categoryStr) {
        this.categories = (categoryStr == null || categoryStr.isEmpty()) ? new String[0] : categoryStr.split("\\s*,\\s*");
    }

    public String getCategoriesStr() {
        return String.join(",", categories);
    }

    @Override
    public String toString() {
        return "Book{\n" +
                "id: " + id + '\n' +
                "name: " + name + '\n' +
                "authors: " + Arrays.toString(authors) + '\n' +
                "publisher: " + publisher + '\n' +
                "publicationDate: " + publicationDate + '\n' +
                "price: " + price + '\n' +
                "categories: " + Arrays.toString(categories) + '\n' +
                '}';
    }
}
