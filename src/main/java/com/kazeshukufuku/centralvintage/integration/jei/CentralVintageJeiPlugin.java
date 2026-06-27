package com.kazeshukufuku.centralvintage.integration.jei;

import com.kazeshukufuku.centralvintage.CentralVintage;
import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideScreen;
import com.kazeshukufuku.centralvintage.registry.CVItems;
import net.ribs.vintagedelight.compat.FermentingCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.integration.jei.transfer.BlazeStoveGuideGhostIngredientHandler;

@JeiPlugin
public class CentralVintageJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return CentralVintage.id("jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(PicklingGuideScreen.class, new BlazeStoveGuideGhostIngredientHandler<>());
        registration.addRecipeClickArea(
                PicklingGuideScreen.class,
                124,
                24,
                42,
                30,
                FermentingCategory.FERMENTING_TYPE
        );
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new PicklingGuideTransferHandler(), FermentingCategory.FERMENTING_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CVItems.PICKLING_GUIDE.get()), FermentingCategory.FERMENTING_TYPE);
    }
}
