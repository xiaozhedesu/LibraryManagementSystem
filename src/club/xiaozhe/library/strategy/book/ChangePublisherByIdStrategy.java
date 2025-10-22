package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class ChangePublisherByIdStrategy extends SQLStrategy {
    public ChangePublisherByIdStrategy() {
        super("UPDATE Books SET publisher = ? WHERE id = ?");
    }

    @Override
    public Object[] param(String param) {
        return param.split("\\s+");
    }
}
