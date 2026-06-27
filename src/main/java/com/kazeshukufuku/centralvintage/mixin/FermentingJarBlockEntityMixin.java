package com.kazeshukufuku.centralvintage.mixin;

import com.kazeshukufuku.centralvintage.content.pickling.PicklingAcceleration;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ribs.vintagedelight.block.entity.FermentingJarBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FermentingJarBlockEntity.class)
public abstract class FermentingJarBlockEntityMixin {
    @Shadow
    private int progress;

    @Shadow
    private int maxProgress;

    @Unique
    private double centralvintage$fermentingAccelerationRemainder;

    @Shadow
    private void increaseCraftingProgress() {
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/ribs/vintagedelight/block/entity/FermentingJarBlockEntity;increaseCraftingProgress()V"
            )
    )
    private void centralvintage$increaseProgressWithBlazeStoveAcceleration(FermentingJarBlockEntity instance) {
        increaseCraftingProgress();

        BlockEntity blockEntity = (BlockEntity) (Object) this;
        double multiplier = PicklingAcceleration.getFermentingSpeedMultiplier(blockEntity.getLevel(), blockEntity.getBlockPos());
        if (multiplier <= 1.0D || progress >= maxProgress) {
            centralvintage$fermentingAccelerationRemainder = 0.0D;
            return;
        }

        double extraProgress = multiplier - 1.0D + centralvintage$fermentingAccelerationRemainder;
        int extraSteps = (int) Math.floor(extraProgress);
        centralvintage$fermentingAccelerationRemainder = extraProgress - extraSteps;

        for (int i = 0; i < extraSteps && progress < maxProgress; i++) {
            increaseCraftingProgress();
        }
    }

    @Inject(method = "resetProgress", at = @At("HEAD"))
    private void centralvintage$resetAccelerationRemainder(CallbackInfo ci) {
        centralvintage$fermentingAccelerationRemainder = 0.0D;
    }
}
