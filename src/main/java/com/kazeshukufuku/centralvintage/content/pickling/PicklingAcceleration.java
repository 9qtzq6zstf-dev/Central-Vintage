package com.kazeshukufuku.centralvintage.content.pickling;

import com.kazeshukufuku.centralvintage.config.CVConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;

public final class PicklingAcceleration {
    public static double getFermentingSpeedMultiplier(@Nullable Level level, BlockPos fermentingJarPos) {
        if (level == null || !CVConfig.ENABLE_BLAZE_STOVE_FERMENTING_ACCELERATION.get()) {
            return 1.0D;
        }

        BlockEntity blockEntityBelow = level.getBlockEntity(fermentingJarPos.below());
        if (!(blockEntityBelow instanceof BlazeStoveBlockEntity blazeStove)) {
            return 1.0D;
        }

        int blazeStatus = blazeStove.getBlazeStatusCode();
        if (blazeStatus >= 3) {
            return CVConfig.SEETHING_FERMENTING_SPEED_MULTIPLIER.get();
        }
        if (blazeStatus >= 2) {
            return CVConfig.NORMAL_BURNING_FERMENTING_SPEED_MULTIPLIER.get();
        }
        return 1.0D;
    }

    private PicklingAcceleration() {
    }
}
