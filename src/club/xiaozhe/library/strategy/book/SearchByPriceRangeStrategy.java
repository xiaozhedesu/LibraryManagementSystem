package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class SearchByPriceRangeStrategy extends SQLStrategy {
    public SearchByPriceRangeStrategy() {
        super("SELECT * FROM Books WHERE price BETWEEN ? AND ? ORDER BY price");
    }

    @Override
    public Object[] param(String param) {
        return Utils.parsePriceRange(param);
    }
}
