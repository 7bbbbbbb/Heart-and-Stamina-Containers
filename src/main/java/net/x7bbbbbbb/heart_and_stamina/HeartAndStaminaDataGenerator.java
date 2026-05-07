package net.x7bbbbbbb.heart_and_stamina;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.x7bbbbbbb.heart_and_stamina.datagen.BlockTagProvider;
import net.x7bbbbbbb.heart_and_stamina.datagen.RegistryDataGenerator;
import net.x7bbbbbbb.heart_and_stamina.world.ModConfiguredFeatures;
import net.x7bbbbbbb.heart_and_stamina.world.ModPlacedFeatures;

public class HeartAndStaminaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(RegistryDataGenerator::new);
	}

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}
