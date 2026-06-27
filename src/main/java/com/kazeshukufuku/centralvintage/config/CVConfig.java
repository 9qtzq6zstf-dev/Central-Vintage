package com.kazeshukufuku.centralvintage.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CVConfig {
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_BLAZE_STOVE_FERMENTING_ACCELERATION;
    public static final ForgeConfigSpec.DoubleValue NORMAL_BURNING_FERMENTING_SPEED_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue SEETHING_FERMENTING_SPEED_MULTIPLIER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.translation("configuration.centralvintage.fermenting_acceleration")
                .push("fermenting_acceleration");
        ENABLE_BLAZE_STOVE_FERMENTING_ACCELERATION = builder
                .comment("Whether fermenting jars placed on a Create: Central Kitchen blaze stove ferment faster based on the stove heat level.")
                .translation("configuration.centralvintage.enableBlazeStoveFermentingAcceleration")
                .define("enableBlazeStoveFermentingAcceleration", true);
        NORMAL_BURNING_FERMENTING_SPEED_MULTIPLIER = builder
                .comment("Fermenting speed multiplier when the blaze stove is normally burning. Matches Create: Central Kitchen's FADING/KINDLED blaze status.")
                .translation("configuration.centralvintage.normalBurningFermentingSpeedMultiplier")
                .defineInRange("normalBurningFermentingSpeedMultiplier", 2.0D, 1.0D, 64.0D);
        SEETHING_FERMENTING_SPEED_MULTIPLIER = builder
                .comment("Fermenting speed multiplier when the blaze stove is super-heated.")
                .translation("configuration.centralvintage.seethingFermentingSpeedMultiplier")
                .defineInRange("seethingFermentingSpeedMultiplier", 4.0D, 1.0D, 64.0D);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    private CVConfig() {
    }
}
