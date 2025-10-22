package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class ChangeNameByIdStrategy extends SQLStrategy {
    public ChangeNameByIdStrategy(){
        super("UPDATE Books SET name = ? WHERE id = ?");
    }

    @Override
    public Object[] param(String param) {
        return param.split("\\s+");
    }
}
