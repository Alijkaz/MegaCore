package ir.jeykey.megacore.gui;

import ir.jeykey.megacore.utils.Common;
import ir.jeykey.megacore.utils.MegaItem;
import ir.jeykey.megacore.MegaPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class MegaGui {
        @Getter private final String name;
        @Getter private final int size;
        @Getter private final Player owner;
        @Getter private Inventory inventory;
        @Getter private final HashMap<ItemStack, HandleEvent> itemHandlers = new HashMap<>();
        @Getter @Setter private ItemStack filler;

        public MegaGui(String name, int size, Player owner) {
                this.name = Common.colorize(name);
                this.size = size;
                this.owner = owner;
        }

        public abstract void setup();

        public void fill(MegaItem item) {
                for (int i = 0; i < getInventory().getSize(); i++) {
                        if (getInventory().getItem(i) == null) place(i, item);
                }
        }

        public void place(int i, MegaItem megaItem, HandleEvent handleEvent) {
                place(i, megaItem.getItemStack(), handleEvent);
        }

        public void place(int i, ItemStack itemStack, HandleEvent handleEvent) {
                place(i, itemStack);
                itemHandlers.put(itemStack, handleEvent);
        }

        public void place(int i, MegaItem item) {
                place(i, item.getItemStack());
        }

        public void place(int i, ItemStack itemStack) {
                getInventory().setItem(i, itemStack);
        }

        public ItemStack getSlot(int i) {
                return getInventory().getItem(i);
        }

        public void open() {
                createInventory();
                setup();
                register();
                getOwner().openInventory(getInventory());
        }

        public void update() {
                getInventory().clear();
                setup();
        }

        public void close() {
                getOwner().closeInventory();
                MegaPlugin.getRegisteredGuis().remove(getOwner());
        }

        public void register() {
                if (!MegaPlugin.getRegisteredGuis().containsKey(getOwner())) {
                        MegaPlugin.getRegisteredGuis().put(getOwner(), this);
                } else {
                        MegaPlugin.getRegisteredGuis().replace(getOwner(), this);
                }
        }

        public void createInventory() {
                this.inventory = Bukkit.createInventory(null, getSize(), getName());
        }

}
