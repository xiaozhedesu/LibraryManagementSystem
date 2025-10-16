package club.xiaozhe.library.exception;

import java.math.BigDecimal;

public class InvalidPriceRangeException extends BookBusinessException {
    public InvalidPriceRangeException(BigDecimal min, BigDecimal max) {
        super("价格区间出错！[" + min + "," + max + "]");
    }
}
