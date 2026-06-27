package com.kazeshukufuku.centralvintage.content.pickling;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class PicklingGuideMenu extends BlazeStoveGuideMenu<PicklingGuide> {
    private static final int INGREDIENT_SLOTS = 6;
    private static final int PLAYER_SLOTS = 36;
    private static final int SECONDARY_RESULT_MENU_SLOT = PLAYER_SLOTS + INGREDIENT_SLOTS + 1;

    public PicklingGuideMenu(MenuType<?> type, int id, Inventory inventory, FriendlyByteBuf buffer) {
        super(type, id, inventory, buffer);
    }

    public PicklingGuideMenu(MenuType<?> type, int id, Inventory inventory, ItemStack stack) {
        super(type, id, inventory, stack);
    }

    public PicklingGuideMenu(MenuType<?> type, int id, Inventory inventory, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inventory, blazeStove);
    }

    @Override
    public PicklingGuide createGuide(ItemStack stack) {
        return PicklingGuide.of(stack);
    }

    public ItemStack getGuideStack() {
        return contentHolder;
    }

    public ItemStack getSecondaryResult() {
        return guide.getSecondaryResult();
    }

    @Override
    protected void addSlots() {
        addPlayerSlots(52, 102);

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                int slot = row * 3 + column;
                addSlot(new CookingIngredientSlot(slot, 61 + column * 18, 31 + row * 18));
            }
        }

        addSlot(new DisplaySlot(6, 183, 27));
        addSlot(new SecondaryResultSlot(183, 44));
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
        if (slotId == SECONDARY_RESULT_MENU_SLOT) {
            return;
        }
        super.clicked(slotId, dragType, clickType, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        if (slotId == SECONDARY_RESULT_MENU_SLOT) {
            return ItemStack.EMPTY;
        }
        return super.quickMoveStack(player, slotId);
    }

    private class SecondaryResultSlot extends Slot {
        private SecondaryResultSlot(int x, int y) {
            super(new SimpleContainer(1), 0, x, y);
        }

        @Override
        public ItemStack getItem() {
            return getSecondaryResult();
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player player) {
            return false;
        }

        @Override
        public void set(ItemStack stack) {
        }
    }
}
