package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class SearchByNameStrategy extends SQLStrategy {
    public SearchByNameStrategy() {
        super("SELECT * FROM Books WHERE name LIKE ? ORDER BY name");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }
}
