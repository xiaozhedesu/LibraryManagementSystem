package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class DeleteByAuthorStrategy extends SQLStrategy {
    public DeleteByAuthorStrategy() {
        super("DELETE FROM Books WHERE authors LIKE ?");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }
}
