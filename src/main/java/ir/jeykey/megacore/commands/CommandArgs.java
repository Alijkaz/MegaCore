package ir.jeykey.megacore.commands;

import lombok.Getter;

import java.util.Arrays;

public class CommandArgs {
    @Getter
    private final String[] args;

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

    /**
     * Executes a block of code on specific arg
     * @param arg The specific arg that we're looking for
     * @param onComplete The code that will be executed
     */
    public void executeOnArg(String arg, ExecuteOnArg onComplete) {
        if (get(0).equalsIgnoreCase(arg)) {
            onComplete.execute(popFirst());
        }
    }

    interface ExecuteOnArg {
        void execute(CommandArgs args);
    }
}
