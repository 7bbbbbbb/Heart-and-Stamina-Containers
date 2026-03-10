package net.x7bbbbbbb.heart_and_stamina.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;

import java.util.Arrays;
import java.util.List;

@Modmenu(modId = HeartAndStamina.MOD_ID)
@Config(name = "heart_and_stamina_config", wrapperName = "HeartAndStaminaConfig")
public class HeartAndStaminaConfigModel{
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    @RangeConstraint(min = 0, max = 10)
    public int healthIncrement = 2;
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    @RangeConstraint(min = 0, max = 10)
    public int deathHealthDecrement = 2;
    @RangeConstraint(min = 1, max = 1000)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public int baseHealth = 20;
    @RangeConstraint(min = 2, max = 1024)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public int maxHealth = 60;
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RangeConstraint(min = 0, max = 50)
    @RestartRequired
    public int staminaIncrement = 10;
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RangeConstraint(min = 0, max = 50)
    @RestartRequired
    public int deathStaminaDecrement = 10;
    @RangeConstraint(min = 1, max = 4999)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public int baseStamina = 100;
    @RangeConstraint(min = 2, max = 5000)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public int maxStamina = 300;
    public boolean showStaminaValueHUD = true;
    @Nest
    public HUDConfig nestedHUDConfig = new HUDConfig();
    public float lootChance = 0.4f;
    public boolean luckIncreasesLoot = true;
    public List<String> mobsDropContainers = Arrays.asList(
            "minecraft:elder_guardian",
            "minecraft:ender_dragon",
            "minecraft:warden",
            "minecraft:wither"
    );

    public static class HUDConfig {
        public int xOffset = 92;
        public int yOffset = -28;
        public String textColor = "#00FF00";
    }
}