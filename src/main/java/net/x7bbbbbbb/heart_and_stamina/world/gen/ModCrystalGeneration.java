package net.x7bbbbbbb.heart_and_stamina.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;
import net.x7bbbbbbb.heart_and_stamina.world.ModPlacedFeatures;

public class ModCrystalGeneration {
    public static void generateCrystals() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                ModPlacedFeatures.HEALTH_CRYSTAL_BLOCK_PLACED_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                ModPlacedFeatures.EXTRA_1_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.END_BARRENS, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                ModPlacedFeatures.EXTRA_2_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
        );
        if (HeartAndStamina.ENDERSCAPE_AVAILABLE) {
            RegistryKey<Biome> enderscapeKey1 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "celestial_grove"));
            RegistryKey<Biome> enderscapeKey2 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "corrupt_barrens"));
            RegistryKey<Biome> enderscapeKey3 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "magnia_fields"));
            RegistryKey<Biome> enderscapeKey4 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "veiled_woodlands"));
            RegistryKey<Biome> enderscapeKey5 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_depths"));
            RegistryKey<Biome> enderscapeKey6 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_skies"));
            RegistryKey<Biome> enderscapeKey7 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_sky_islands"));

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(enderscapeKey1, enderscapeKey2, enderscapeKey3, enderscapeKey4, enderscapeKey5, enderscapeKey6, enderscapeKey7),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_2_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
            );
        }
        if (HeartAndStamina.AERIALHELL_AVAILABLE) {
            RegistryKey<DimensionOptions> AERIALHELL = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of("aerialhell", "aerial_hell"));
            BiomeModifications.addFeature(
                    context -> context.canGenerateIn(AERIALHELL),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_2_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
            );
        }
        if (HeartAndStamina.DEEPERDARKER_AVAILABLE) {
            RegistryKey<DimensionOptions> OTHERSIDE = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of("deeperdarker", "otherside"));
            BiomeModifications.addFeature(
                    context -> context.canGenerateIn(OTHERSIDE),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_3_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
            );
        }
        if (HeartAndStamina.TERRALITH_AVAILABLE) {
            RegistryKey<Biome> terralithKey1 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_autumn"));
            RegistryKey<Biome> terralithKey2 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_spring"));
            RegistryKey<Biome> terralithKey3 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_summer"));
            RegistryKey<Biome> terralithKey4 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_winter"));

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(terralithKey1, terralithKey2, terralithKey3, terralithKey4),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_4_HEALTH_CRYSTAL_BLOCK_PLACED_KEY
            );
        }
        if (HeartAndStamina.AVAILABLE_MAX_STAMINA_ID != null) {
            BiomeModifications.addFeature(
                    BiomeSelectors.foundInOverworld(),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.STAMINA_CRYSTAL_BLOCK_PLACED_KEY
            );
            BiomeModifications.addFeature(
                    BiomeSelectors.foundInTheNether(),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_1_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
            );
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.END_BARRENS, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS),
                    GenerationStep.Feature.UNDERGROUND_DECORATION,
                    ModPlacedFeatures.EXTRA_2_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
            );
            if (HeartAndStamina.ENDERSCAPE_AVAILABLE) {
                RegistryKey<Biome> enderscapeKey1 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "celestial_grove"));
                RegistryKey<Biome> enderscapeKey2 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "corrupt_barrens"));
                RegistryKey<Biome> enderscapeKey3 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "magnia_fields"));
                RegistryKey<Biome> enderscapeKey4 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "veiled_woodlands"));
                RegistryKey<Biome> enderscapeKey5 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_depths"));
                RegistryKey<Biome> enderscapeKey6 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_skies"));
                RegistryKey<Biome> enderscapeKey7 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("enderscape", "void_sky_islands"));

                BiomeModifications.addFeature(
                        BiomeSelectors.includeByKey(enderscapeKey1, enderscapeKey2, enderscapeKey3, enderscapeKey4, enderscapeKey5, enderscapeKey6, enderscapeKey7),
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.EXTRA_2_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
                );
            }
            if (HeartAndStamina.AERIALHELL_AVAILABLE) {
                RegistryKey<DimensionOptions> AERIALHELL = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of("aerialhell", "aerial_hell"));
                BiomeModifications.addFeature(
                        context -> context.canGenerateIn(AERIALHELL),
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.EXTRA_2_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
                );
            }
            if (HeartAndStamina.DEEPERDARKER_AVAILABLE) {
                RegistryKey<DimensionOptions> OTHERSIDE = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of("deeperdarker", "otherside"));
                BiomeModifications.addFeature(
                        context -> context.canGenerateIn(OTHERSIDE),
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.EXTRA_3_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
                );
            }
            if (HeartAndStamina.TERRALITH_AVAILABLE) {
                RegistryKey<Biome> terralithKey1 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_autumn"));
                RegistryKey<Biome> terralithKey2 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_spring"));
                RegistryKey<Biome> terralithKey3 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_summer"));
                RegistryKey<Biome> terralithKey4 = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("terralith", "skylands_winter"));

                BiomeModifications.addFeature(
                        BiomeSelectors.includeByKey(terralithKey1, terralithKey2, terralithKey3, terralithKey4),
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.EXTRA_4_STAMINA_CRYSTAL_BLOCK_PLACED_KEY
                );
            }
        }
    }
}
