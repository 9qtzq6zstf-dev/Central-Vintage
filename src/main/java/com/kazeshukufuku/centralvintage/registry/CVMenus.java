package com.kazeshukufuku.centralvintage.registry;

import com.kazeshukufuku.centralvintage.CentralVintage;
import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CVMenus {
    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CentralVintage.MOD_ID);

    public static final RegistryObject<MenuType<PicklingGuideMenu>> PICKLING_GUIDE =
            MENUS.register("pickling_guide", () -> IForgeMenuType.create(
                    (id, inventory, buffer) -> new PicklingGuideMenu(CVMenus.PICKLING_GUIDE.get(), id, inventory, buffer)
            ));

    private CVMenus() {
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
