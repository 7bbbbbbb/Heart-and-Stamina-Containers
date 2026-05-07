package net.x7bbbbbbb.heart_and_stamina;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.x7bbbbbbb.heart_and_stamina.block.HeartAndStaminaBlocks;

public class HeartAndStaminaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(HeartAndStaminaBlocks.HEALTH_CRYSTAL_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(HeartAndStaminaBlocks.STAMINA_CRYSTAL_BLOCK, RenderLayer.getCutout());

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
