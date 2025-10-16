package club.xiaozhe.library.exception;

public class IllegalPriceException extends BookBusinessException {
    public IllegalPriceException(String price) {
        super("价格格式不对：" + price + "，请注意价格须为正数");
    }
}
