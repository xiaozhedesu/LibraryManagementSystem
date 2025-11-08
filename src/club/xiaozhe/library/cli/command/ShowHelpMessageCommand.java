package club.xiaozhe.library.cli.command;

import club.xiaozhe.library.support.HelpMessage;

/**
 * 显示help信息的指令
 */
public class ShowHelpMessageCommand extends Command {
    String op = "";

    /**
     * 无参构造方法，默认为help指令
     */
    public ShowHelpMessageCommand() {
    }

    /**
     * 有参构造方法，匹配help子指令
     *
     * @param op 指令选择
     */
    public ShowHelpMessageCommand(String op) {
        this.op = op;
    }

    @Override
    public void execute() {
        switch (this.op) {
            case "" -> System.out.println(HelpMessage.HELP);
            case "change" -> System.out.println(HelpMessage.HELP_CHANGE);
            case "delete" -> System.out.println(HelpMessage.HELP_DELETE);
            case "search" -> System.out.println(HelpMessage.HELP_SEARCH);
        }
    }
}
