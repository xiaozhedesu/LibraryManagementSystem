package club.xiaozhe.library.support;

public final class HelpMessage {
    public static final String HELP = """
            已支持的指令：
            help 帮助
            exit 退出
            stop 退出
            add 添加
            delete 删除
            change 修改
            search 搜索
            size 查询书籍总数""";
    public static final String HELP_SEARCH = """
            已支持的指令(search)：
            all 查找全部
            id <id> 按照id查询
            name <name> 按照书名查询
            author <author> 按照作者查询
            publisher <publisher> 按照出版社查询
            category <category> 按照分类查询
            priceRange <min> <max> 按照价格区间查询""";
    public static final String HELP_DELETE = """
            已支持的指令(delete)：
            id <id> 按照id删除（单个）
            name <name> 按照书名删除（批量）
            author <author> 按照作者删除（批量）
            publisher <publisher> 按照出版社删除（批量）
            category <category> 按照分类删除（批量）
            priceRange <min> <max> 按照价格区间删除（批量）""";
    public static final String HELP_CHANGE = """
            已支持的指令(change)：
            name <name> <id>         根据id修改书名
            authors <authors> <id>   根据id整批替换作者
            addAuthor <author> <id>  根据id追加一位作者
            publisher <publisher> <id> 根据id修改出版社
            date <yyyy-MM-dd> <id>   根据id修改出版日期
            price <price> <id>       根据id修改价格（正数）
            categories <categories> <id> 根据id整批替换分类
            addCategory <category> <id>  根据id追加一个分类""";

    private HelpMessage() {
    }
}
