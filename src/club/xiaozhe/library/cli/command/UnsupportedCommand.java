package club.xiaozhe.library.cli.command;

/**
 * 当没有匹配到其他所以的指令时使用，替代抛出异常，给用户输入错误信息
 */
public class UnsupportedCommand extends Command {
    @Override
    public void execute() {
        System.out.println("❌ 不支持的指令，请使用 help 查询指令。");
    }
}
