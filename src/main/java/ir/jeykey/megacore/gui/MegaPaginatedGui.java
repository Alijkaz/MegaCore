package ir.jeykey.megacore.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MegaPaginatedGui extends MegaGui {
        @Getter private static final HashMap<Player, Integer> pagination = new HashMap<>();

        public MegaPaginatedGui(String name, int size, Player owner) {
                super(name, size, owner);
                setPage(1);
        }

        public boolean nextPage() {
                setPage(getPage() + 1);
                return true;
        }

        public boolean previousPage() {
                if (getPage() == 0) {
                        return false;
                } else {
                        setPage(getPage() - 1);
                        return true;
                }
        }

        public void setPage(int page) {
                if (pagination.containsKey(getOwner())) pagination.replace(getOwner(), page);
                else pagination.put(getOwner(), page);
                open();
        }

        public int getPage() {
                return pagination.get(getOwner());
        }

        public abstract int getMaxItemsPerPage();
}
