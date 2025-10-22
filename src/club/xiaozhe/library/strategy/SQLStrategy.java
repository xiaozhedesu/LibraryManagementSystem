package club.xiaozhe.library.strategy;

/**
 * sql语句查询策略
 */
public abstract class SQLStrategy {
    /**
     * 空参数
     */
    public static final Object[] NO_PARAM = new Object[0];
    private final String sql;

    /**
     * 提供给子类，重写时在构造方法中调用
     *
     * @param sql sql串
     */
    protected SQLStrategy(String sql) {
        this.sql = sql;
    }

    /**
     * 此方法返回类中所存储的sql语句
     *
     * @return sql串
     */
    public String sql() {
        return sql;
    }

    /**
     * 解析参数
     *
     * @param param 参数
     * @return 解析后的参数数组
     */
    public Object[] param(String param) {
        return new Object[]{param};
    }
}
