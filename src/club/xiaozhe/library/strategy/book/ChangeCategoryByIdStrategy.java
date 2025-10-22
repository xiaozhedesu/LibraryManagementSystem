package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class ChangeCategoryByIdStrategy extends SQLStrategy {
    public ChangeCategoryByIdStrategy() {
        super("UPDATE Books SET categories = ? WHERE id = ?");
    }


    @Override
    public Object[] param(String param) {
        return param.split("\\s+");
    }
}
