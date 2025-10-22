package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;
import club.xiaozhe.library.support.Utils;

class DeleteByCategoryStrategy extends SQLStrategy {
    public DeleteByCategoryStrategy() {
        super("DELETE FROM Books WHERE categories LIKE ?");
    }

    public Object[] param(String param) {
        return super.param(Utils.likeOf(param));
    }

}
