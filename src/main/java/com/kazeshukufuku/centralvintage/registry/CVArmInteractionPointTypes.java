package com.kazeshukufuku.centralvintage.registry;

import com.kazeshukufuku.centralvintage.CentralVintage;
import com.kazeshukufuku.centralvintage.content.mechanicalarm.FermentingJarPoint;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.Registry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CVArmInteractionPointTypes {
    public static final FermentingJarPoint.Type FERMENTING_JAR = new FermentingJarPoint.Type();

    private CVArmInteractionPointTypes() {
    }

    public static void register(FMLCommonSetupEvent event) {
        Registry.register(
                CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE,
                CentralVintage.id("fermenting_jar"),
                FERMENTING_JAR
        );
        ArmInteractionPointType.init();
    }
}
