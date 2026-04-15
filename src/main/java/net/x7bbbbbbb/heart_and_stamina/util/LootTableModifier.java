package net.x7bbbbbbb.heart_and_stamina.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;
import net.x7bbbbbbb.heart_and_stamina.item.HeartItems;
import net.x7bbbbbbb.heart_and_stamina.item.StaminaItems;

import java.util.HashSet;
import java.util.Set;

public class LootTableModifier {
    public static void modifyLootTables() {
        HeartAndStamina.LOGGER.info("[Heart and Stamina] Modifying mob drops.");
        Set<Identifier> targetLootTables = new HashSet<>();

        for (String mobStr : HeartAndStamina.CONFIG.mobsDropContainers()) {
            Identifier mobId = Identifier.tryParse(mobStr);
            if (mobId != null) {
                Identifier lootTableId = Identifier.of(mobId.getNamespace(), "entities/" + mobId.getPath());
                targetLootTables.add(lootTableId);
            } else {
                HeartAndStamina.LOGGER.info("[Heart and Stamina] Invalid mob identifier in config: '{}'. Skipping!", mobStr);
            }
        }
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier tableId = key.getValue();
            LootPool.Builder poolBuilder;
            if (tableId.getPath().startsWith("entities/")) {
                if (targetLootTables.contains(key.getValue())) {
                    if (HeartAndStamina.AVAILABLE_MAX_STAMINA_ID != null) {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER))
                                .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER));
                    } else {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER));
                    }
                    tableBuilder.pool(poolBuilder);
                }
            } else if (tableId.getPath().startsWith("chests/")) {
                if (HeartAndStamina.CONFIG.luckIncreasesLoot()) {
                    if (HeartAndStamina.AVAILABLE_MAX_STAMINA_ID != null) {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .bonusRolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(HeartAndStamina.CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD))
                                .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER_SHARD));
                    } else {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .bonusRolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(HeartAndStamina.CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD));
                    }
                } else {
                    if (HeartAndStamina.AVAILABLE_MAX_STAMINA_ID != null) {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(HeartAndStamina.CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD))
                                .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER_SHARD));
                    } else {
                        poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(HeartAndStamina.CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD));
                    }
                }
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}

