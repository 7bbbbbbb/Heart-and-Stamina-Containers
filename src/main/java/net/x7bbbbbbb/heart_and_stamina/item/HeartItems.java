package net.x7bbbbbbb.heart_and_stamina.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;
import net.x7bbbbbbb.heart_and_stamina.item.custom.HeartContainerItem;

public class HeartItems {
    public static final Item HEART_CONTAINER = registerItem("heart_container", new HeartContainerItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC)));
    public static final Item HEART_CONTAINER_SHARD = registerItem("heart_container_shard", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(HeartAndStamina.MOD_ID, name), item);
    }

    public static void registerModItems(){
        HeartAndStamina.LOGGER.info("[Heart and Stamina Containers] Registering Heart items.");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(HEART_CONTAINER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(HEART_CONTAINER_SHARD);
        });
    }
}
