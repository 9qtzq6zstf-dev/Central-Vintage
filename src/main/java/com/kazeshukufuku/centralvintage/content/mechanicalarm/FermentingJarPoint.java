package com.kazeshukufuku.centralvintage.content.mechanicalarm;

import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuide;
import com.kazeshukufuku.centralvintage.content.pickling.PicklingGuideItem;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.ribs.vintagedelight.block.entity.FermentingJarBlockEntity;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;

public class FermentingJarPoint extends ArmInteractionPoint {
    private static final int INPUT_SLOT_COUNT = 6;
    private static final int CONTAINER_SLOT = 6;
    private static final int FIRST_OUTPUT_SLOT = 7;
    private static final int SECOND_OUTPUT_SLOT = 8;
    private static final int VIRTUAL_SLOT_COUNT = 9;

    private LazyOptional<IItemHandler> cachedOutputHandler = LazyOptional.empty();

    public FermentingJarPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, 0.625, 0);
    }

    @Override
    protected @Nullable IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!(blockEntity instanceof FermentingJarBlockEntity)) {
                return null;
            }
            cachedHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
        }
        return cachedHandler.orElse(null);
    }

    private @Nullable IItemHandler getOutputHandler() {
        if (!cachedOutputHandler.isPresent()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!(blockEntity instanceof FermentingJarBlockEntity)) {
                return null;
            }
            cachedOutputHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN);
        }
        return cachedOutputHandler.orElse(null);
    }

    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        PicklingGuide guide = getGuide();
        if (guide == null || guide.getResult().isEmpty()) {
            return stack;
        }

        IItemHandler handler = getHandler();
        if (handler == null) {
            return stack;
        }

        if (handler.getStackInSlot(CONTAINER_SLOT).isEmpty() && guide.isContainer(stack)) {
            return handler.insertItem(CONTAINER_SLOT, stack, simulate);
        }

        boolean[] matchingSlots = new boolean[INPUT_SLOT_COUNT];
        int matchingCount = 0;
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (handler.getStackInSlot(slot).isEmpty()
                    && guide.needIngredient(slot)
                    && guide.isIngredient(slot, stack)) {
                matchingSlots[slot] = true;
                matchingCount++;
            }
        }

        if (matchingCount == 0) {
            return stack;
        }

        int insertions = Math.min(matchingCount, stack.getCount());
        ItemStack remainder = stack.copy();
        remainder.shrink(insertions);
        int insertedCount = 0;
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (matchingSlots[slot] && insertedCount < insertions) {
                ItemStack inserted = stack.copy();
                inserted.setCount(1);
                handler.insertItem(slot, inserted, simulate);
                insertedCount++;
            }
        }
        return remainder;
    }

    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        int actualSlot = getActualExtractSlot(slot);
        if (actualSlot == FIRST_OUTPUT_SLOT || actualSlot == SECOND_OUTPUT_SLOT) {
            IItemHandler outputHandler = getOutputHandler();
            if (outputHandler == null) {
                return ItemStack.EMPTY;
            }
            return outputHandler.extractItem(actualSlot - FIRST_OUTPUT_SLOT, amount, simulate);
        }

        IItemHandler handler = getHandler();
        if (handler == null) {
            return ItemStack.EMPTY;
        }

        PicklingGuide guide = getGuide();
        if (guide == null || guide.getResult().isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (actualSlot < INPUT_SLOT_COUNT) {
            ItemStack stored = handler.getStackInSlot(actualSlot);
            if (!stored.isEmpty() && !guide.isIngredient(actualSlot, stored)) {
                return handler.extractItem(actualSlot, amount, simulate);
            }
        } else if (actualSlot == CONTAINER_SLOT) {
            ItemStack stored = handler.getStackInSlot(actualSlot);
            if (!stored.isEmpty() && !guide.isContainer(stored)) {
                return handler.extractItem(actualSlot, amount, simulate);
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotCount() {
        return VIRTUAL_SLOT_COUNT;
    }

    private int getActualExtractSlot(int virtualSlot) {
        return switch (virtualSlot) {
            case 0 -> FIRST_OUTPUT_SLOT;
            case 1 -> SECOND_OUTPUT_SLOT;
            case 8 -> CONTAINER_SLOT;
            default -> virtualSlot - 2;
        };
    }

    private @Nullable PicklingGuide getGuide() {
        BlockEntity blockEntityBelow = level.getBlockEntity(pos.below());
        if (!(blockEntityBelow instanceof BlazeStoveBlockEntity blazeStove)) {
            return null;
        }

        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof PicklingGuideItem)) {
            return null;
        }

        return PicklingGuide.of(guideStack);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof FermentingJarBlockEntity
                    && level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }

        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new FermentingJarPoint(this, level, pos, state);
        }

        @Override
        public int getPriority() {
            return 100;
        }
    }
}
