package club.xiaozhe.library.exception;

public class InsufficientParameterException extends BookBusinessException {
    public InsufficientParameterException(String message) {
        super("缺少参数：" + message);
    }
}
