package com.kazeshukufuku.centralvintage.client;

import com.kazeshukufuku.centralvintage.registry.CVItems;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.foundation.ponder.scene.BlazeStoveScenes;
import plus.dragons.createcentralkitchen.foundation.ponder.tag.FDPonderTags;

public class CVPonderPlugin implements PonderPlugin {
    private static final String CENTRAL_KITCHEN = "create_central_kitchen";

    @Override
    public String getModId() {
        return CENTRAL_KITCHEN;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ResourceLocation picklingGuide = CVItems.PICKLING_GUIDE.getId();

        helper.addStoryBoard(
                picklingGuide,
                centralKitchen("blaze_stove/intro"),
                BlazeStoveScenes::intro,
                FDPonderTags.COOKING
        );
        helper.addStoryBoard(
                picklingGuide,
                centralKitchen("blaze_stove/automation"),
                BlazeStoveScenes::automation,
                FDPonderTags.COOKING
        );
        helper.addStoryBoard(
                picklingGuide,
                centralKitchen("blaze_stove/heat_source"),
                BlazeStoveScenes::heat_source,
                FDPonderTags.COOKING
        );
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(FDPonderTags.COOKING)
                .add(CVItems.PICKLING_GUIDE.getId());
    }

    private static ResourceLocation centralKitchen(String path) {
        return new ResourceLocation(CENTRAL_KITCHEN, path);
    }
}
