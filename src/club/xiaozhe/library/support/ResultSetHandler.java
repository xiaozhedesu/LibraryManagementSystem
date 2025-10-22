package club.xiaozhe.library.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 对查询返回的ResultSet对象进行处理的接口
 * @param <T> 对象中拿到数据后封装的对象
 */
public interface ResultSetHandler<T> {
    /**
     * 函数式接口
     * @return 相关对象列表
     */
    List<T> handler(ResultSet set)  throws SQLException;
}
