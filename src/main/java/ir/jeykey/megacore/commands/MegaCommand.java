package ir.jeykey.megacore.commands;

import ir.jeykey.megacore.utils.Common;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public abstract class MegaCommand extends Command {
    @Getter
    private String usedLabel;

    @Getter
    private CommandArgs args;

    @Getter
    private MegaCommandSender sender;

    @Getter @Setter
    private String permission = null, noPermissionMessage = null;

    private final List<MegaSubCommand> subCommands = new ArrayList<>();

    @Getter @Setter
    private boolean isPlayerCommand = true;

    public MegaCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String usedLabel, String[] args) {
        this.args = new CommandArgs(args);
        this.usedLabel = usedLabel;
        this.sender = new MegaCommandSender(sender);

        if (isPlayerCommand() && !getSender().isPlayer()) return true;

        if (getPermission() != null && !getSender().getCommandSender().hasPermission(getPermission())) {
            if (getNoPermissionMessage() != null) {
                getSender().send(Common.colorize(getNoPermissionMessage()));
            }
            return true;
        }

        boolean isSubCommand = false;
        if (args.length > 0) {
            for (MegaSubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.setArgs(getArgs().popFirst());
                    subCommand.setSender(getSender());
                    subCommand.execute();
                    isSubCommand = true;
                    break;
                }
            }
        }

        if (!isSubCommand) {
            onCommand();
        }

        return true;
    }

    public void addSubCommand(MegaSubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    public abstract void onCommand();

    public void register() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(getName(), this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
