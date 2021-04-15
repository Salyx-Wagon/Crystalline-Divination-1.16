package salyx.crystalline.divination.client.guis;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.common.containers.CognitiveRuneContainer;
import salyx.crystalline.divination.common.tiles.runes.StorageRuneTile;

@OnlyIn(Dist.CLIENT)
public class CognitiveRuneScreen extends ContainerScreen<CognitiveRuneContainer> {
    private static final ResourceLocation COGNITIVE_RUNE_GUI = new ResourceLocation(CrystalDiv.MOD_ID,
            "textures/guis/cognitive_rune.png");
    private Button button1;
    private Button button2;
    private TextFieldWidget searchBox;
    private int scroll;
    private CognitiveRuneContainer screenContainer;
    private boolean onStart;
    private int rows;

    public CognitiveRuneScreen(CognitiveRuneContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 221;

        this.scroll = 0;
        this.screenContainer = screenContainer;
        this.onStart = false;
        this.rows = 3;

        this.searchBox = new TextFieldWidget(this.font, 0, 0, 60, 10, new TranslationTextComponent("Search"));
        this.searchBox.setMaxStringLength(50);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);

        this.button1 = new Button(this.titleX, this.titleY+54, 32, 10, new TranslationTextComponent("down"), new IPressable(){
            @Override
            public void onPress(Button p_onPress_1_) {
                
                if(scroll < rows-6){
                    scroll++;
                }
                updateSlots();
            }
        });
        this.button2 = new Button(this.titleX, this.titleY+54, 32, 10, new TranslationTextComponent("up"), new IPressable(){
            @Override
            public void onPress(Button p_onPress_1_) {
                
                if(scroll > 0){
                    scroll--;
                }
                updateSlots();
            }
        });
    }

    public void updateSlots(){

        List<Integer> fullSlots = new ArrayList<Integer>();
        List<Integer> emptySlots = new ArrayList<Integer>();
        for(int i = 0; i < screenContainer.inventorySlots.size(); i++){
            if(screenContainer.getSlot(i).inventory instanceof StorageRuneTile){
                if(!screenContainer.getSlot(i).getStack().isEmpty()){
                    fullSlots.add(i);
                }
                else{
                    emptySlots.add(i);
                }
            }
        }
        


        this.rows = Math.round((fullSlots.size()+emptySlots.size())/9);
        for(int index = 0; index < fullSlots.size(); index++){
            int row = Math.round(index/9)-this.scroll;
            int col = index % 9;
            if(!(row > 5 || row < 0)){
                screenContainer.inventorySlots.set(fullSlots.get(index), new Slot(screenContainer.getSlot(fullSlots.get(index)).inventory,
                screenContainer.getSlot(fullSlots.get(index)).getSlotIndex(), 8+col*18, 99-(4-row)*18-10));
            }
            else{
                screenContainer.inventorySlots.set(fullSlots.get(index), new Slot(screenContainer.getSlot(fullSlots.get(index)).inventory,
                screenContainer.getSlot(fullSlots.get(index)).getSlotIndex(), 8+col*18, 99-(4-1000)*18-10));
            }
            screenContainer.getSlot(fullSlots.get(index)).slotNumber=fullSlots.get(index);
        }
        for(int index = 0; index < emptySlots.size(); index++){
            int row = Math.round((index+fullSlots.size())/9)-this.scroll;
            int col = (index+fullSlots.size()) % 9;
            if(!(row > 5 || row < 0)){
                screenContainer.inventorySlots.set(emptySlots.get(index), new Slot(screenContainer.getSlot(emptySlots.get(index)).inventory,
                screenContainer.getSlot(emptySlots.get(index)).getSlotIndex(), 8+col*18, 99-(4-row)*18-10));
            }
            else{
                screenContainer.inventorySlots.set(emptySlots.get(index), new Slot(screenContainer.getSlot(emptySlots.get(index)).inventory,
                screenContainer.getSlot(emptySlots.get(index)).getSlotIndex(), 8+col*18, 99-(4-1000)*18-10));
            }
            screenContainer.getSlot(emptySlots.get(index)).slotNumber=emptySlots.get(index);
        }

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(!this.onStart){
            this.onStart = true;
            this.addButton(this.button1);
            this.addButton(this.button2);
            this.children.add(this.searchBox);
            this.updateSlots();
        }
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        this.button1.render(matrixStack, mouseX, mouseY, partialTicks);
        this.button1.x = this.guiLeft + 136;
        this.button1.y = this.guiTop + 71+54;
        
        this.button2.render(matrixStack, mouseX, mouseY, partialTicks);
        this.button2.x = this.guiLeft + 100;
        this.button2.y = this.guiTop + 71+54;
        
        this.searchBox.setFocused2(true);
        //this.searchBox.render(matrixStack, mouseX, mouseY, partialTicks);
        this.searchBox.x = this.guiLeft;
        this.searchBox.y = this.guiTop;

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX,
                (float) this.playerInventoryTitleY+54, 4210752);
    }

    @Deprecated
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
            int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bindTexture(COGNITIVE_RUNE_GUI);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }
    @Override
    public void tick() {
        super.tick();
        //this.updateSlots();
    }
    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
        this.updateSlots();
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if(delta >= 1.0){
            if(scroll > 0){
                scroll--;
            }
            updateSlots();
        }
        if(delta < 0){
            if(scroll < rows-6){
                scroll++;
            }
            updateSlots();
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
    
}
