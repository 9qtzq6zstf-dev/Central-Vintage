package com.kazeshukufuku.centralvintage.client;

import com.kazeshukufuku.centralvintage.CentralVintage;
import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideScreen;
import com.kazeshukufuku.centralvintage.registry.CVMenus;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CentralVintage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CVClientEvents {
    private CVClientEvents() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CVPonderPlugin());
        event.enqueueWork(() -> MenuScreens.register(CVMenus.PICKLING_GUIDE.get(), PicklingGuideScreen::new));
    }
}
