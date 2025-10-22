package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class DeleteByNameStrategy extends SQLStrategy {
    public DeleteByNameStrategy() {
        super("DELETE FROM Books WHERE name LIKE ?");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }

}
