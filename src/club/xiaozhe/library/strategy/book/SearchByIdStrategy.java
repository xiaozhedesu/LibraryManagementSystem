package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class SearchByIdStrategy extends SQLStrategy {
    public SearchByIdStrategy(){
        super("SELECT * FROM Books WHERE id = ? ORDER BY id");
    }
}
