package club.xiaozhe.library.exception;

public class InvalidDateFormatException extends BookBusinessException{
    public InvalidDateFormatException(String dateStr) {
        super("时间格式不正确（需要YYYY-MM-DD）：" + dateStr);
    }
}
