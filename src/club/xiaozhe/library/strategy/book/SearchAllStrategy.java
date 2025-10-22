package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class SearchAllStrategy extends SQLStrategy {
    public SearchAllStrategy() {
        super("SELECT * FROM Books");
    }

    @Override
    public Object[] param(String param) {
        return NO_PARAM;
    }
}
