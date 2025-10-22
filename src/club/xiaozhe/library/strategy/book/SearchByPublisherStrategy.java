package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class SearchByPublisherStrategy extends SQLStrategy {
    public SearchByPublisherStrategy() {
        super("SELECT * FROM Books WHERE publisher LIKE ? ORDER BY publisher");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }

}
