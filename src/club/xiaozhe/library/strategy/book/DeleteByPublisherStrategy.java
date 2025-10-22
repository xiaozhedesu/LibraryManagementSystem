package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class DeleteByPublisherStrategy extends SQLStrategy {
    public DeleteByPublisherStrategy() {
        super("DELETE FROM Books WHERE publisher LIKE ?");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }

}
