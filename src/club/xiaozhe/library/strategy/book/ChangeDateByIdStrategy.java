package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class ChangeDateByIdStrategy extends SQLStrategy {
    public ChangeDateByIdStrategy() {
        super("UPDATE Books SET publicationDate = ? WHERE id = ?");
    }

    @Override
    public Object[] param(String param) {
        if (Utils.isTimeMatched(param))
            return super.param(param);
        else
            // 正常应该会在前端进行校验，理应不会运行
            return super.param("2000-01-01");
    }
}
