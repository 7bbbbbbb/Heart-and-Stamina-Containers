package net.x7bbbbbbb.heart_and_stamina.world;

import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.x7bbbbbbb.heart_and_stamina.block.HeartAndStaminaBlocks;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> HEALTH_CRYSTAL_BLOCK_KEY = registerKey("health_crystal_block");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STAMINA_CRYSTAL_BLOCK_KEY = registerKey("stamina_crystal_block");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        context.register(HEALTH_CRYSTAL_BLOCK_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(HeartAndStaminaBlocks.HEALTH_CRYSTAL_BLOCK))));
        context.register(STAMINA_CRYSTAL_BLOCK_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(HeartAndStaminaBlocks.STAMINA_CRYSTAL_BLOCK))));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(HeartAndStamina.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}