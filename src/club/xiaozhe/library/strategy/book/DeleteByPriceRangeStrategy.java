package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class DeleteByPriceRangeStrategy extends SQLStrategy {
    public DeleteByPriceRangeStrategy() {
        super("DELETE FROM Books WHERE price BETWEEN ? AND ?");
    }

    @Override
    public Object[] param(String param) {
        return Utils.parsePriceRange(param);
    }
}
