package com.kazeshukufuku.centralvintage.registry;

import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuide;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class CVCapabilities {
    public static final Capability<PicklingGuide> PICKLING_GUIDE =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    private CVCapabilities() {
    }
}
