package com.kazeshukufuku.centralvintage.integration.jei;

import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideMenu;
import com.kazeshukufuku.centralvintage.registry.CVMenus;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.ribs.vintagedelight.compat.FermentingCategory;
import net.ribs.vintagedelight.recipe.FermentingRecipe;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.entry.network.FDNetworkEntries;

import java.util.Optional;

public class PicklingGuideTransferHandler implements IRecipeTransferHandler<PicklingGuideMenu, FermentingRecipe> {
    @Override
    public Class<? extends PicklingGuideMenu> getContainerClass() {
        return PicklingGuideMenu.class;
    }

    @Override
    public Optional<MenuType<PicklingGuideMenu>> getMenuType() {
        return Optional.of(CVMenus.PICKLING_GUIDE.get());
    }

    @Override
    public RecipeType<FermentingRecipe> getRecipeType() {
        return FermentingCategory.FERMENTING_TYPE;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(
            PicklingGuideMenu menu,
            FermentingRecipe recipe,
            IRecipeSlotsView slots,
            Player player,
            boolean maxTransfer,
            boolean doTransfer
    ) {
        if (!doTransfer) {
            return null;
        }

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (int slot = 0; slot < 6; slot++) {
            ItemStack stack = ItemStack.EMPTY;
            if (slot < ingredients.size()) {
                ItemStack[] matchingStacks = ingredients.get(slot).getItems();
                if (matchingStacks.length > 0) {
                    stack = matchingStacks[0];
                }
            }
            menu.getSlot(36 + slot).set(stack);
        }

        FDNetworkEntries.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(menu));
        return null;
    }
}
