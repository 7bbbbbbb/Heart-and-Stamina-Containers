package net.x7bbbbbbb.heart_and_stamina.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;

import java.util.List;

public class HeartContainerItem extends Item {
    public HeartContainerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final var stack = user.getStackInHand(hand);
        if (world.isClient)
            return TypedActionResult.fail(stack);

        // Increasing hearts by adding an attribute modifier
        final var attr = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        // Get max health
        boolean hasModifier = attr.hasModifier(HeartAndStamina.HEALTH_MODIFIER_ID);
        int maxHealth = hasModifier ? (int) attr.getModifier(HeartAndStamina.HEALTH_MODIFIER_ID).value() + 20 : HeartAndStamina.CONFIG.baseHealth();

        // Check if at max allowable health
        if (maxHealth >= HeartAndStamina.CONFIG.maxHealth()) {
            user.sendMessage(Text.translatable("message.heart_and_stamina.health_limit reached").formatted(Formatting.RED), true);
            return TypedActionResult.fail(stack);
        }

        // Play sound
        user.playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);

        // Decrement stack
        stack.decrement(1);

        // Increment max health
        int increment = Math.min(HeartAndStamina.CONFIG.healthIncrement(), HeartAndStamina.CONFIG.maxHealth() - maxHealth);
        maxHealth += increment;
        // Calculate modifier value
        int modifierValue = maxHealth - 20;

        // Apply new modifier value
        EntityAttributeModifier modifier = new EntityAttributeModifier(HeartAndStamina.HEALTH_MODIFIER_ID, modifierValue, EntityAttributeModifier.Operation.ADD_VALUE);
        attr.overwritePersistentModifier(modifier);

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.heart_and_stamina.heart_container.1"));
            if (HeartAndStamina.CONFIG.deathHealthDecrement() > 0) {
                tooltip.add(Text.translatable("tooltip.heart_and_stamina.heart_container.2"));
            }
        } else {
            tooltip.add(Text.translatable("tooltip.heart_and_stamina.shift_down"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}