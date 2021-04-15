package salyx.crystalline.divination.client.guis;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.common.containers.AdvancedRuneContainer;

@OnlyIn(Dist.CLIENT)
public class AdvancedRuneScreen extends ContainerScreen<AdvancedRuneContainer>{

    private static final ResourceLocation ADVANCED_RUNE_GUI = new ResourceLocation(CrystalDiv.MOD_ID, "textures/guis/custom_block.png");

    public AdvancedRuneScreen(AdvancedRuneContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 201;

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),
        (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, 4210752);
    }

    @Deprecated
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bindTexture(ADVANCED_RUNE_GUI);
        int x = (this.width - this.xSize)/2;
        int y = (this.height - this.ySize)/2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }
    
}
