package net.bearster.bearsterschessmod.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.bearster.bearsterschessmod.BearstersChessMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PromotionScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =
            BearstersChessMod.loc("textures/gui/chess_piece/promotion_gui.png");

    public PromotionScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - 176) / 2;
        int y = (height - 166) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, 176, 166);
    }
}
