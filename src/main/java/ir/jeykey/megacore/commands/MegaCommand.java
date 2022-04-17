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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class MegaCommand extends Command {
    @Getter
    private String label;

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
    public boolean execute(CommandSender sender, String label, String[] args) {
        this.args = new CommandArgs(args);
        this.label = label;
        this.sender = new MegaCommandSender(sender);

        if (isPlayerCommand() && !getSender().isPlayer()) return true;

        if (getPermission() != null && !getSender().getCommandSender().hasPermission(getPermission())) {
            if (getNoPermissionMessage() != null) {
                getSender().send(Common.colorize(getNoPermissionMessage()));
            }
            return true;
        }

        AtomicBoolean isSubCommand = new AtomicBoolean(false);
        if (args.length > 0) {
            subCommands.forEach((subCommand) -> {
                if (args[0].equalsIgnoreCase(subCommand.getLabel())) {
                    subCommand.setArgs(getArgs().popFirst());
                    subCommand.setSender(getSender());
                    subCommand.execute();
                    isSubCommand.set(true);
                }
            });
        }

        if (isSubCommand.get()) return true;

        onCommand();

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

    static class CommandArgs {
        @Getter
        private String[] args;

        public CommandArgs(String[] args) {
            this.args = args;
        }

        public boolean hasAny() {
            return (this.args != null && this.args.length != 0);
        }

        public String get(int index) {
            if (args.length > index) {
                return args[index];
            }
            return null;
        }

        /**
         * Returns a new CommandArgs instance without the first index
         */
        public CommandArgs popFirst() {
            return new CommandArgs(Arrays.copyOfRange(args, 1, args.length));
        }
    }
}
