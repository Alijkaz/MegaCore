package ir.jeykey.megacore.commands;

import ir.jeykey.megacore.utils.Common;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public abstract class MegaSubCommand {
    @Getter
    private final String name;

    @Getter @Setter
    private CommandArgs args;

    @Getter @Setter
    private MegaCommandSender sender;

    @Getter @Setter
    private String permission = null, noPermissionMessage = null;

    @Getter @Setter
    private boolean isPlayerCommand = true;

    public MegaSubCommand(String name) {
        this.name = name;
    }

    public void execute() {
        if (isPlayerCommand() && !getSender().isPlayer()) return;

        if (getPermission() != null && !getSender().getCommandSender().hasPermission(getPermission())) {
            if (getNoPermissionMessage() != null) {
                getSender().send(Common.colorize(getNoPermissionMessage()));
            }
            return;
        }

        onCommand();
    }

    public abstract void onCommand();
}
