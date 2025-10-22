package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class SearchByCategoryStrategy extends SQLStrategy {
    public SearchByCategoryStrategy() {
        super("SELECT * FROM Books WHERE categories LIKE ? ORDER BY categories");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }

}
