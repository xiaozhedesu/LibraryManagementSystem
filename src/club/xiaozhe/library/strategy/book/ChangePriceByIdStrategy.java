package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

import java.math.BigDecimal;

class ChangePriceByIdStrategy extends SQLStrategy {
    public ChangePriceByIdStrategy() {
        super("UPDATE Books SET price = ? WHERE id = ?");
    }

    @Override
    public Object[] param(String param) {
        if (Utils.isNumStr(param))
            return new Object[]{new BigDecimal(param)};
        else
            // 正常应该会在前端进行校验，理应不会运行
            return new Object[]{BigDecimal.ZERO};
    }
}
