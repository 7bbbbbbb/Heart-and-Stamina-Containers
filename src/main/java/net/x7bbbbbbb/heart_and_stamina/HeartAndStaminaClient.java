package net.x7bbbbbbb.heart_and_stamina;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class HeartAndStaminaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (HeartAndStamina.AVAILABLE_MAX_STAMINA_ID != null) {
            EntityAttribute attr = Registries.ATTRIBUTE.get(HeartAndStamina.AVAILABLE_MAX_STAMINA_ID);
            String hex = HeartAndStamina.CONFIG.nestedHUDConfig.textColor().replace("#", "");
            int color = Integer.parseInt(hex, 16);
            HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player == null) return;
                if (!HeartAndStamina.CONFIG.showStaminaValueHUD()) return;
                if (client.player.isCreative() || client.player.isSpectator()) return;
                if (!client.options.hudHidden && client.currentScreen == null) {
                    int width = drawContext.getScaledWindowWidth();
                    int height = drawContext.getScaledWindowHeight();
                    int x = (width / 2) + HeartAndStamina.CONFIG.nestedHUDConfig.xOffset();
                    int y = height + HeartAndStamina.CONFIG.nestedHUDConfig.yOffset();

                    EntityAttributeInstance attribute = client.player.getAttributeInstance(Registries.ATTRIBUTE.getEntry(attr));
                    if (attribute != null) {
                        String text = String.format("%.0f", attribute.getValue());

                        drawContext.drawText(
                                client.textRenderer,
                                Text.literal(text),
                                x, y,
                                color,
                                true
                        );
                    }
                }
            });
        }
    }
}
