package club.xiaozhe.library.gui.components;

import java.io.Serializable;

public class BookFieldArrays implements Serializable {
    /**
     * 用于执行对应模式的数组
     */
    public static final String[] FIELDS = {
            "all", "id", "name", "author", "publisher", "category"
    };
    /**
     * 用于显示模式的数组
     */
    public static final String[] SHOW_MODES = {
            "所有", "id", "书名", "作者", "出版社", "分类"
    };
}