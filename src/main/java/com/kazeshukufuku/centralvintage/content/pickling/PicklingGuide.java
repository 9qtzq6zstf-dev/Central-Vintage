package com.kazeshukufuku.centralvintage.content.pickling;

import com.kazeshukufuku.centralvintage.registry.CVCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.ribs.vintagedelight.recipe.FermentingRecipe;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuide;

import java.util.Optional;

public class PicklingGuide extends BlazeStoveGuide {
    private static final int INGREDIENT_SLOTS = 6;

    private final LazyOptional<PicklingGuide> capability = LazyOptional.of(() -> this);

    public PicklingGuide(ItemStack owner) {
        super(owner, INGREDIENT_SLOTS);
    }

    public static PicklingGuide of(ItemStack stack) {
        return stack.getCapability(CVCapabilities.PICKLING_GUIDE)
                .orElseThrow(() -> new UnsupportedOperationException(stack.getItem() + " is not a pickling guide"));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CVCapabilities.PICKLING_GUIDE) {
            return capability.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void updateRecipe(Level level) {
        Optional<FermentingRecipe> recipe = level.getRecipeManager()
                .getAllRecipesFor(FermentingRecipe.Type.INSTANCE)
                .stream()
                .filter(this::matchesIngredients)
                .findFirst();

        if (recipe.isPresent()) {
            FermentingRecipe fermentingRecipe = recipe.get();
            inventory.setStackInSlot(ingredientSize, fermentingRecipe.getResultItem(level.registryAccess()));
            container = fermentingRecipe.getContainerItemStack().copy();
        } else {
            inventory.setStackInSlot(ingredientSize, ItemStack.EMPTY);
            container = ItemStack.EMPTY;
        }
    }

    private boolean matchesIngredients(FermentingRecipe recipe) {
        NonNullList<Ingredient> remaining = NonNullList.create();
        remaining.addAll(recipe.getIngredients());

        for (int slot = 0; slot < ingredientSize; slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            boolean matched = false;
            for (int i = 0; i < remaining.size(); i++) {
                if (remaining.get(i).test(stack)) {
                    remaining.remove(i);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return remaining.isEmpty();
    }
}
