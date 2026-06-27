package com.kazeshukufuku.centralvintage.content.pickling;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class PicklingGuideMenu extends BlazeStoveGuideMenu<PicklingGuide> {
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

    @Override
    protected void addSlots() {
        addPlayerSlots(52, 102);

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                int slot = row * 3 + column;
                addSlot(new CookingIngredientSlot(slot, 61 + column * 18, 31 + row * 18));
            }
        }

        addSlot(new DisplaySlot(6, 183, 41));
    }
}
