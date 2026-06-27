package com.kazeshukufuku.centralvintage.registry;

import com.kazeshukufuku.centralvintage.CentralVintage;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class CVCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CentralVintage.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CENTRAL_VINTAGE = TABS.register(
            "centralvintage",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.centralvintage"))
                    .icon(() -> new ItemStack(CVItems.PICKLING_GUIDE.get()))
                    .displayItems((parameters, output) -> output.accept(CVItems.PICKLING_GUIDE.get()))
                    .build()
    );

    private CVCreativeModeTabs() {
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
