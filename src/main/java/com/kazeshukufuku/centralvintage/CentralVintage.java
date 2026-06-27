package com.kazeshukufuku.centralvintage;

import com.kazeshukufuku.centralvintage.config.CVConfig;
import com.kazeshukufuku.centralvintage.registry.CVCreativeModeTabs;
import com.kazeshukufuku.centralvintage.registry.CVItems;
import com.kazeshukufuku.centralvintage.registry.CVMenus;
import com.kazeshukufuku.centralvintage.registry.CVArmInteractionPointTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CentralVintage.MOD_ID)
public final class CentralVintage {
    public static final String MOD_ID = "centralvintage";

    public CentralVintage() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CVConfig.COMMON_SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        CVItems.register(modBus);
        CVCreativeModeTabs.register(modBus);
        CVMenus.register(modBus);
        modBus.addListener(CVArmInteractionPointTypes::register);
        modBus.addListener(this::addCreativeTabItems);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(CVItems.PICKLING_GUIDE);
        }
    }
}
