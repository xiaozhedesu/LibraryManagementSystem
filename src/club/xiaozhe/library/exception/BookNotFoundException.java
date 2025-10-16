package club.xiaozhe.library.exception;

public class BookNotFoundException extends BookBusinessException {
    public BookNotFoundException(String id) {
        super("未找到id=" + id + "的书籍");
    }
}
