package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class DeleteByIdStrategy extends SQLStrategy {
    public DeleteByIdStrategy() {
        super("DELETE FROM Books WHERE id = ?");
    }
}
