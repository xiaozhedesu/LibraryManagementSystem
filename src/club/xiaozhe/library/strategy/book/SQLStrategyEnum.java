package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

/**
 * <p>通过枚举类型实现策略选择
 * <p>使用<code>of(String)</code>自动选择对应的策略对象，<code>sql()</code>和<code>param(String)</code>提供代理访问策略对象的接口
 */
public enum SQLStrategyEnum {
    /* ------------------- Search ------------------- */
    SEARCH_ALL(new SearchAllStrategy()),
    SEARCH_ID(new SearchByIdStrategy()),
    SEARCH_NAME(new SearchByNameStrategy()),
    SEARCH_AUTHOR(new SearchByAuthorStrategy()),
    SEARCH_PUBLISHER(new SearchByPublisherStrategy()),
    SEARCH_PRICERANGE(new SearchByPriceRangeStrategy()),
    SEARCH_CATEGORY(new SearchByCategoryStrategy()),

    /* ------------------- Change ------------------- */
    CHANGE_NAME(new ChangeNameByIdStrategy()),
    CHANGE_AUTHOR(new ChangeAuthorByIdStrategy()),
    CHANGE_PUBLISHER(new ChangePublisherByIdStrategy()),
    CHANGE_DATE(new ChangeDateByIdStrategy()),
    CHANGE_PRICE(new ChangePriceByIdStrategy()),
    CHANGE_CATEGORY(new ChangeCategoryByIdStrategy()),

    /* ------------------- Delete ------------------- */
    DELETE_ID(new DeleteByIdStrategy()),
    DELETE_NAME(new DeleteByNameStrategy()),
    DELETE_AUTHOR(new DeleteByAuthorStrategy()),
    DELETE_PUBLISHER(new DeleteByPublisherStrategy()),
    DELETE_PRICERANGE(new DeleteByPriceRangeStrategy()),
    DELETE_CATEGORY(new DeleteByCategoryStrategy()),

    /* -------------------- Add -------------------- */
    ADD_ALL(new AddStrategy());

    private final SQLStrategy strategy;

    SQLStrategyEnum(SQLStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 根据传入指令获取对应查询策略
     *
     * @param key 指令
     * @return 对应的枚举对象
     */
    public static SQLStrategyEnum ofSearch(String key) {
        return valueOf("SEARCH_" + key.toUpperCase());
    }

    /**
     * 根据传入指令获取对应删除策略
     *
     * @param key 指令
     * @return 对应的枚举对象
     */
    public static SQLStrategyEnum ofDelete(String key) {
        return valueOf("DELETE_" + key.toUpperCase());
    }

    /**
     * 根据传入指令获取对应改变策略
     *
     * @param key 指令
     * @return 对应的枚举对象
     */
    public static SQLStrategyEnum ofChange(String key) {
        return valueOf("CHANGE_" + key.toUpperCase());
    }

    /**
     * 根据传入指令获取对应添加策略
     *
     * @param key 指令
     * @return 对应的枚举对象
     */
    public static SQLStrategyEnum ofAdd(String key) {
        return valueOf("ADD_" + key.toUpperCase());
    }

    /**
     * 策略对象<code>sql()</code>方法的代理
     *
     * @return sql串
     */
    public String sql() {
        return strategy.sql();
    }

    /**
     * 策略对象<code>param()</code>方法的代理
     *
     * @param param 参数字符串
     * @return 参数数组
     */
    public Object[] param(String param) {
        return strategy.param(param);
    }
}