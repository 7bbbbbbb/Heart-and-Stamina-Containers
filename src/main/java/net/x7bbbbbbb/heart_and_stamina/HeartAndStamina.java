package net.x7bbbbbbb.heart_and_stamina;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.x7bbbbbbb.heart_and_stamina.config.HeartAndStaminaConfig;
import net.x7bbbbbbb.heart_and_stamina.item.HeartItems;
import net.x7bbbbbbb.heart_and_stamina.item.StaminaItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HeartAndStamina implements ModInitializer {
    public static final String MOD_ID = "heart_and_stamina";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final HeartAndStaminaConfig CONFIG = HeartAndStaminaConfig.createAndLoad();
    /** Modifiers */
    public static final Identifier HEALTH_MODIFIER_ID = Identifier.of(MOD_ID, "health");
    public static final Identifier STAMINA_MODIFIER_ID = Identifier.of(MOD_ID, "stamina");

    public static Identifier AVAILABLE_MAX_STAMINA_ID = null;

    /** Entity ID's */
    public static final Set<Identifier> EXACT_MOBS = new HashSet<>();
    public static final List<Pattern> REGEX_MOBS = new ArrayList<>();
    public static final Set<TagKey<EntityType<?>>> TAG_MOBS = new HashSet<>();

    @Override
    public void onInitialize() {
        HeartItems.registerModItems();
        if (FabricLoader.getInstance().isModLoaded("staminafortweakers")) {
            StaminaItems.registerModItems();
            AVAILABLE_MAX_STAMINA_ID = Identifier.of("staminafortweakers", "generic.max_stamina");
        }

        // Adding items to loot tables
        for (String entry : CONFIG.mobsDropContainers()) {
            if (entry.startsWith("#")) {
                // It's a Tag
                Identifier id = Identifier.tryParse(entry.substring(1));
                if (id != null) TAG_MOBS.add(TagKey.of(Registries.ENTITY_TYPE.getKey(), id));
            } else if (entry.contains("*") || entry.contains("[") || entry.contains("(")) {
                // It's likely a Regex
                try {
                    REGEX_MOBS.add(Pattern.compile(entry));
                } catch (PatternSyntaxException e) {
                    System.err.println("Invalid Regex in X7B Config: " + entry);
                }
            } else {
                // It's a standard ID
                Identifier id = Identifier.tryParse(entry);
                if (id != null) EXACT_MOBS.add(id);
            }
        }

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier tableId = key.getValue();
            if (!tableId.getPath().startsWith("entities/")) return;

            String rawMobPath = tableId.getPath().replaceFirst("entities/", "");
            Identifier mobId = Identifier.of(tableId.getNamespace(), rawMobPath);

            boolean matches = EXACT_MOBS.contains(mobId);

            if (!matches) {
                String fullIdString = mobId.toString();
                matches = REGEX_MOBS.stream().anyMatch(p -> p.matcher(fullIdString).matches());
            }

            if (!matches) {
                var entityEntry = Registries.ENTITY_TYPE.getEntry(mobId);

                if (entityEntry.isPresent()) {
                    matches = TAG_MOBS.stream().anyMatch(tag -> entityEntry.get().isIn(tag));
                }
            }

            if (matches) {
                if (AVAILABLE_MAX_STAMINA_ID != null) {
                    tableBuilder.pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1))
                            .with(ItemEntry.builder(HeartItems.HEART_CONTAINER))
                            .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER)));
                } else {
                    tableBuilder.pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1))
                            .with(ItemEntry.builder(HeartItems.HEART_CONTAINER)));
                }
            }
        });

        if (AVAILABLE_MAX_STAMINA_ID != null) {
            LootTableEvents.MODIFY.register((key1, tableBuilder1, source1, registries1) -> {
                Identifier id = key1.getValue();
                if (id.getPath().startsWith("chests/")) {
                    if (CONFIG.luckIncreasesLoot()) {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .bonusRolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD))
                                .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER_SHARD));
                        tableBuilder1.pool(poolBuilder);
                    } else {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD))
                                .with(ItemEntry.builder(StaminaItems.STAMINA_CONTAINER_SHARD));
                        tableBuilder1.pool(poolBuilder);
                    }
                }
            });
        } else {
            LootTableEvents.MODIFY.register((key1, tableBuilder1, source1, registries1) -> {
                Identifier id = key1.getValue();
                if (id.getPath().startsWith("chests/")) {
                    if (CONFIG.luckIncreasesLoot()) {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .bonusRolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD));
                        tableBuilder1.pool(poolBuilder);
                    } else {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(CONFIG.lootChance()))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                                .with(ItemEntry.builder(HeartItems.HEART_CONTAINER_SHARD));
                        tableBuilder1.pool(poolBuilder);
                    }
                }
            });
        }

        // Called when new entity is loaded
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            // Only handle player entities
            if (!(entity instanceof PlayerEntity player))
                return;

            var attr1 = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            // Set player to base health if new player (has no modifier)
            assert attr1 != null;
            if (!attr1.hasModifier(HEALTH_MODIFIER_ID)) {
                final var health = CONFIG.baseHealth();

                EntityAttributeModifier modifier = new EntityAttributeModifier(HEALTH_MODIFIER_ID, health - 20,
                        EntityAttributeModifier.Operation.ADD_VALUE);
                attr1.addPersistentModifier(modifier);
                player.setHealth(health);
            }
            if (AVAILABLE_MAX_STAMINA_ID != null) {
                var attr2 = Registries.ATTRIBUTE.getEntry(AVAILABLE_MAX_STAMINA_ID).map(player::getAttributeInstance).orElse(null);
                assert attr2 != null;
                if (!attr2.hasModifier(STAMINA_MODIFIER_ID)) {
                    final var stamina = CONFIG.baseStamina();

                    EntityAttributeModifier modifier = new EntityAttributeModifier(STAMINA_MODIFIER_ID, stamina - 100,
                            EntityAttributeModifier.Operation.ADD_VALUE);
                    attr2.addPersistentModifier(modifier);
                }
            }
        });

        // Called when player respawns
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, arg2) -> {
            var oldAttr1 = oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            // Set new player modifier
            assert oldAttr1 != null;
            if (oldAttr1.hasModifier(HEALTH_MODIFIER_ID)) {
                final var oldModifier1 = oldAttr1.getModifier(HEALTH_MODIFIER_ID);

                // Lower the modifier value
                assert oldModifier1 != null;
                double newValue = oldModifier1.value() - CONFIG.deathHealthDecrement();

                // Safety check
                if (newValue < CONFIG.baseHealth() - 20) {
                    newValue = CONFIG.baseHealth() - 20;
                }

                int maxHealth = (int) newValue + 20;

                // Create a new modifier with the reduced value
                EntityAttributeModifier newModifier = new EntityAttributeModifier(
                        HEALTH_MODIFIER_ID,
                        newValue,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );

                // Apply the newly created modifier
                newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(newModifier);
                newPlayer.setHealth(maxHealth);
            }
            if (AVAILABLE_MAX_STAMINA_ID != null) {
                var oldAttr2 = Registries.ATTRIBUTE.getEntry(AVAILABLE_MAX_STAMINA_ID).map(oldPlayer::getAttributeInstance).orElse(null);
                assert oldAttr2 != null;
                if (oldAttr2.hasModifier(STAMINA_MODIFIER_ID)) {
                    final var oldModifier2 = oldAttr2.getModifier(STAMINA_MODIFIER_ID);

                    // Lower the modifier value
                    assert oldModifier2 != null;
                    double newValue = oldModifier2.value() - CONFIG.deathStaminaDecrement();

                    // Safety check
                    if (newValue < CONFIG.baseStamina() - 100) {
                        newValue = CONFIG.baseStamina() - 100;
                    }

                    // Create a new modifier with the reduced value
                    EntityAttributeModifier newModifier = new EntityAttributeModifier(
                            STAMINA_MODIFIER_ID,
                            newValue,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    );

                    // Apply the newly created modifier
                    Registries.ATTRIBUTE.getEntry(AVAILABLE_MAX_STAMINA_ID).map(newPlayer::getAttributeInstance).orElse(null).addPersistentModifier(newModifier);
                }
            }
        });
    }
}