package net.x7bbbbbbb.heart_and_stamina.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.x7bbbbbbb.heart_and_stamina.HeartAndStamina;

import java.util.List;

public class StaminaContainerItem extends Item {
    public StaminaContainerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final var stack = user.getStackInHand(hand);
        if (world.isClient)
            return TypedActionResult.fail(stack);

        // Increase stamina by adding an attribute modifier
        final var attr = Registries.ATTRIBUTE.getEntry(HeartAndStamina.AVAILABLE_MAX_STAMINA_ID).map(user::getAttributeInstance).orElse(null);

        // Get max stamina
        boolean hasModifier = attr.hasModifier(HeartAndStamina.STAMINA_MODIFIER_ID);
        int maxStamina = hasModifier ? (int) attr.getModifier(HeartAndStamina.STAMINA_MODIFIER_ID).value() + 100 : HeartAndStamina.CONFIG.baseStamina();

        // Check if at max allowable stamina
        if (maxStamina >= HeartAndStamina.CONFIG.maxStamina()) {
            user.sendMessage(Text.translatable("message.heart_and_stamina.stamina_limit reached").formatted(Formatting.RED), true);
            return TypedActionResult.fail(stack);
        }

        // Play sound
        user.playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);

        // Decrement stack
        stack.decrement(1);

        // Increment max stamina
        int increment = Math.min(HeartAndStamina.CONFIG.staminaIncrement(), HeartAndStamina.CONFIG.maxStamina() - maxStamina);
        maxStamina += increment;
        // Calculate modifier value
        int modifierValue = maxStamina - 100;

        // Apply new modifier value
        EntityAttributeModifier modifier = new EntityAttributeModifier(HeartAndStamina.STAMINA_MODIFIER_ID, modifierValue, EntityAttributeModifier.Operation.ADD_VALUE);
        attr.overwritePersistentModifier(modifier);

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.heart_and_stamina.stamina_container.1"));
            if (HeartAndStamina.CONFIG.deathStaminaDecrement() > 0) {
                tooltip.add(Text.translatable("tooltip.heart_and_stamina.stamina_container.2"));
            }
        } else {
            tooltip.add(Text.translatable("tooltip.heart_and_stamina.shift_down"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
