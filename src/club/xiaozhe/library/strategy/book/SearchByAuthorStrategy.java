package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class SearchByAuthorStrategy extends SQLStrategy {
    public SearchByAuthorStrategy() {
        super("SELECT * FROM Books WHERE authors LIKE ? ORDER BY authors");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }
}
