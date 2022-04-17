package ir.jeykey.megacore.commands;

import ir.jeykey.megacore.utils.Common;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MegaCommandSender {
    @Getter
    private final CommandSender commandSender;

    public MegaCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public boolean isPlayer() {
        return commandSender instanceof Player;
    }

    public Player getPlayer() {
        if (isPlayer()) {
            return (Player) commandSender;
        }
        return null;
    }

    public void send(String message) {
        this.commandSender.sendMessage(Common.colorize(message));
    }
}
