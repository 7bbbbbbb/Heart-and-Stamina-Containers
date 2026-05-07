package net.x7bbbbbbb.heart_and_stamina;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.x7bbbbbbb.heart_and_stamina.block.HeartAndStaminaBlocks;
import net.x7bbbbbbb.heart_and_stamina.config.HeartAndStaminaConfig;
import net.x7bbbbbbb.heart_and_stamina.item.HeartItems;
import net.x7bbbbbbb.heart_and_stamina.item.StaminaItems;
import net.x7bbbbbbb.heart_and_stamina.util.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.x7bbbbbbb.heart_and_stamina.world.gen.ModWorldGeneration;

public class HeartAndStamina implements ModInitializer {
    public static final String MOD_ID = "heart_and_stamina";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final HeartAndStaminaConfig CONFIG = HeartAndStaminaConfig.createAndLoad();
    /** Modifiers */
    public static final Identifier HEALTH_MODIFIER_ID = Identifier.of(MOD_ID, "health");
    public static final Identifier STAMINA_MODIFIER_ID = Identifier.of(MOD_ID, "stamina");

    public static Identifier AVAILABLE_MAX_STAMINA_ID = null;
    public static Boolean AERIALHELL_AVAILABLE = false;
    public static Boolean DEEPERDARKER_AVAILABLE = false;
    public static Boolean ENDERSCAPE_AVAILABLE = false;
    public static Boolean TERRALITH_AVAILABLE = false;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("staminafortweakers")) {
            AVAILABLE_MAX_STAMINA_ID = Identifier.of("staminafortweakers", "generic.max_stamina");
        }
        if (FabricLoader.getInstance().isModLoaded("aerialhell")) {
            AERIALHELL_AVAILABLE = true;
        }
        if (FabricLoader.getInstance().isModLoaded("deeperdarker")) {
            DEEPERDARKER_AVAILABLE = true;
        }
        if (FabricLoader.getInstance().isModLoaded("enderscape")) {
            ENDERSCAPE_AVAILABLE = true;
        }
        if (FabricLoader.getInstance().isModLoaded("terralith")) {
            TERRALITH_AVAILABLE = true;
        }
        HeartItems.registerModItems();
        StaminaItems.registerModItems();
        HeartAndStaminaBlocks.registerBlocks();
        LootTableModifier.modifyLootTables();
        if (CONFIG.generateCrystals()) {
            ModWorldGeneration.generateModWorldGen();
        }
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof PlayerEntity player))
                return;
            var attr1 = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
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

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, arg2) -> {
            var oldAttr1 = oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            assert oldAttr1 != null;
            if (oldAttr1.hasModifier(HEALTH_MODIFIER_ID)) {
                final var oldModifier1 = oldAttr1.getModifier(HEALTH_MODIFIER_ID);
                assert oldModifier1 != null;
                double newValue = oldModifier1.value() - CONFIG.deathHealthDecrement();
                if (newValue < CONFIG.baseHealth() - 20) {
                    newValue = CONFIG.baseHealth() - 20;
                }
                int maxHealth = (int) newValue + 20;
                EntityAttributeModifier newModifier = new EntityAttributeModifier(
                        HEALTH_MODIFIER_ID,
                        newValue,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );
                newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(newModifier);
                newPlayer.setHealth(maxHealth);
            }
            if (AVAILABLE_MAX_STAMINA_ID != null) {
                var oldAttr2 = Registries.ATTRIBUTE.getEntry(AVAILABLE_MAX_STAMINA_ID).map(oldPlayer::getAttributeInstance).orElse(null);
                assert oldAttr2 != null;
                if (oldAttr2.hasModifier(STAMINA_MODIFIER_ID)) {
                    final var oldModifier2 = oldAttr2.getModifier(STAMINA_MODIFIER_ID);
                    assert oldModifier2 != null;
                    double newValue = oldModifier2.value() - CONFIG.deathStaminaDecrement();
                    if (newValue < CONFIG.baseStamina() - 100) {
                        newValue = CONFIG.baseStamina() - 100;
                    }
                    EntityAttributeModifier newModifier = new EntityAttributeModifier(
                            STAMINA_MODIFIER_ID,
                            newValue,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    );
                    Registries.ATTRIBUTE.getEntry(AVAILABLE_MAX_STAMINA_ID).map(newPlayer::getAttributeInstance).orElse(null).addPersistentModifier(newModifier);
                }
            }
        });
    }
}