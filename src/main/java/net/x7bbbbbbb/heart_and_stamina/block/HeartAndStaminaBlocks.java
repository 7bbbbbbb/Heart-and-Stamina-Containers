package net.x7bbbbbbb.heart_and_stamina.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;
import net.x7bbbbbbb.heart_and_stamina.block.custom.HealthCrystalBlock;
import net.x7bbbbbbb.heart_and_stamina.block.custom.StaminaCrystalBlock;

public class HeartAndStaminaBlocks {
    public static final Block HEALTH_CRYSTAL_BLOCK = registerBlock("health_crystal_block",
            new HealthCrystalBlock(7, 3, AbstractBlock.Settings.create().mapColor(MapColor.RED).solid().nonOpaque().strength(2f).requiresTool().sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance((state) -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block STAMINA_CRYSTAL_BLOCK = registerBlock("stamina_crystal_block",
            new StaminaCrystalBlock(7, 3, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).solid().nonOpaque().strength(2f).requiresTool().sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance((state) -> 5).pistonBehavior(PistonBehavior.DESTROY)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(HeartAndStamina.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(HeartAndStamina.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {
        HeartAndStamina.LOGGER.info("[Heart and Stamina Containers] Registering blocks.");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(HeartAndStaminaBlocks.HEALTH_CRYSTAL_BLOCK);
            entries.add(HeartAndStaminaBlocks.STAMINA_CRYSTAL_BLOCK);
        });
    }
}
