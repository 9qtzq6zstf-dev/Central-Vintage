package com.kazeshukufuku.centralvintage.registry;

import com.kazeshukufuku.centralvintage.CentralVintage;
import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CVItems {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CentralVintage.MOD_ID);

    public static final RegistryObject<Item> PICKLING_GUIDE = ITEMS.register(
            "pickling_guide",
            () -> new PicklingGuideItem(new Item.Properties().stacksTo(1))
    );

    private CVItems() {
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
