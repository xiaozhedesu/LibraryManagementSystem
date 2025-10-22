package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class ChangeAuthorByIdStrategy extends SQLStrategy {
    public ChangeAuthorByIdStrategy() {
        super("UPDATE Books SET authors = ? WHERE id = ?");
    }

    @Override
    public Object[] param(String param) {
        return param.split("\\s+");
    }
}
