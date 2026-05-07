package net.x7bbbbbbb.heart_and_stamina.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> HEALTH_CRYSTAL_BLOCK_PLACED_KEY = registerKey("health_crystal_block_placed");
    public static final RegistryKey<PlacedFeature> EXTRA_1_HEALTH_CRYSTAL_BLOCK_PLACED_KEY = registerKey("health_crystal_block_placed_extra_1");
    public static final RegistryKey<PlacedFeature> EXTRA_2_HEALTH_CRYSTAL_BLOCK_PLACED_KEY = registerKey("health_crystal_block_placed_extra_2");
    public static final RegistryKey<PlacedFeature> EXTRA_3_HEALTH_CRYSTAL_BLOCK_PLACED_KEY = registerKey("health_crystal_block_placed_extra_3");
    public static final RegistryKey<PlacedFeature> EXTRA_4_HEALTH_CRYSTAL_BLOCK_PLACED_KEY = registerKey("health_crystal_block_placed_extra_4");
    public static final RegistryKey<PlacedFeature> STAMINA_CRYSTAL_BLOCK_PLACED_KEY = registerKey("stamina_crystal_block_placed");
    public static final RegistryKey<PlacedFeature> EXTRA_1_STAMINA_CRYSTAL_BLOCK_PLACED_KEY = registerKey("stamina_crystal_block_placed_extra_1");
    public static final RegistryKey<PlacedFeature> EXTRA_2_STAMINA_CRYSTAL_BLOCK_PLACED_KEY = registerKey("stamina_crystal_block_placed_extra_2");
    public static final RegistryKey<PlacedFeature> EXTRA_3_STAMINA_CRYSTAL_BLOCK_PLACED_KEY = registerKey("stamina_crystal_block_placed_extra_3");
    public static final RegistryKey<PlacedFeature> EXTRA_4_STAMINA_CRYSTAL_BLOCK_PLACED_KEY = registerKey("stamina_crystal_block_placed_extra_4");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        context.register(HEALTH_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.HEALTH_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2), // Rarity: 1 in 32 chunks
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(40)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(
                                ConstantIntProvider.create(1)
                        ),
                        BiomePlacementModifier.of() // Only place in valid biomes
                )
        ));
        context.register(EXTRA_1_HEALTH_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.HEALTH_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(4),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(128)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_2_HEALTH_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.HEALTH_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(4),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(256)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_3_HEALTH_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.HEALTH_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(128)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_4_HEALTH_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.HEALTH_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(200), YOffset.fixed(275)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(STAMINA_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.STAMINA_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2), // Rarity: 1 in 32 chunks
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(40)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(
                                ConstantIntProvider.create(1)
                        ),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_1_STAMINA_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.STAMINA_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(4),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(128)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_2_STAMINA_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.STAMINA_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(4),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(256)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_3_STAMINA_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.STAMINA_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(128)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
        context.register(EXTRA_4_STAMINA_CRYSTAL_BLOCK_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.STAMINA_CRYSTAL_BLOCK_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(2),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(200), YOffset.fixed(275)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 24),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                )
        ));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(HeartAndStamina.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}