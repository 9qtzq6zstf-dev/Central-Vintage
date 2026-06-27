package com.kazeshukufuku.centralvintage.content.pickling;

import com.kazeshukufuku.centralvintage.registry.CVCapabilities;
import com.kazeshukufuku.centralvintage.registry.CVMenus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideItem;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class PicklingGuideItem extends BlazeStoveGuideItem<PicklingGuide> {
    public PicklingGuideItem(Properties properties) {
        super(properties);
    }

    @Override
    protected Capability<PicklingGuide> getGuideCapability() {
        return CVCapabilities.PICKLING_GUIDE;
    }

    @Override
    protected BlazeStoveGuideMenu<PicklingGuide> createGuideMenu(int id, Inventory inventory, ItemStack stack) {
        return new PicklingGuideMenu(CVMenus.PICKLING_GUIDE.get(), id, inventory, stack);
    }

    @Override
    protected BlazeStoveGuideMenu<PicklingGuide> createGuideMenu(int id, Inventory inventory, BlazeStoveBlockEntity blazeStove) {
        return new PicklingGuideMenu(CVMenus.PICKLING_GUIDE.get(), id, inventory, blazeStove);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PicklingGuide(stack);
    }
}
