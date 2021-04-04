package salyx.crystalline.divination.client.guis.ter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import salyx.crystalline.divination.common.tiles.RunicInterceptorTile;
import salyx.crystalline.divination.core.init.ItemInit;

@SuppressWarnings("unused")
public class RunicInterceptorTileEntityRenderer extends TileEntityRenderer<RunicInterceptorTile> {
    public RenderMaterial TEXTURE_BOOK;// = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("crystaldiv:entity/enchanting_table_book"));
    private final BookModel modelBook = new BookModel();
    String defaultBookTexture = "entity/enchanting_table_book";

    public static int r;

    private Minecraft mc = Minecraft.getInstance();

    public RunicInterceptorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(RunicInterceptorTile te, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
            ClientPlayerEntity player = mc.player;
            int lightLevel = getLightLevel(te.getWorld(), te.getPos().up());
            double d = 1;
            if(!te.getPos().withinDistance(player.getPosition(), 8)){
                if(!te.getPos().withinDistance(player.getPosition(), 16)){
                    d = 0;
                }
                
                else{d = 52/(te.getPos().distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), true)-8);}
            }
            if(d>0){
                if(!te.getItem().isEmpty()){
                    //if(te.getItem().getItem().getDefaultInstance().isItemEqual(Items.PAPER.getDefaultInstance())){
                    //    TEXTURE_BOOK = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("crystaldiv:entities/default_runic_parchment"));
                    //    renderBook(te, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, (float)(0.5*d));
                    //}
                    //else{
                        renderItem(te.getItem(), new double[] {0.5d, 1d, 0.5d}, Vector3f.YP.rotationDegrees(r/10),
                        matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                    //}
                }
            }
            ITextComponent label = te.getItem().hasDisplayName() ? te.getItem().getDisplayName() :new TranslationTextComponent(te.getItem().getTranslationKey());
            if(player.getHeldItemMainhand().getItem() == ItemInit.ADVANCED_ITEM.get()) {
                renderLabel(matrixStackIn, bufferIn, lightLevel, new double[] {0.5d, 1.3d, 0.5d}, label , 0xffffff);   
            } 
        
    }
    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
    IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {

        matrixStack.push();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.rotate(rotation);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        matrixStack.pop();
    }
    private void renderLabel(MatrixStack stack, IRenderTypeBuffer buffer, int lightLevel, double[] corner, ITextComponent text, int color) {
        
        FontRenderer font = mc.fontRenderer;
        
        stack.push();
        float scale = 0.01f;
        int opacity = (int) (.4f * 255.0f) << 24;
        float offset = (float) (-font.getStringPropertyWidth(text)/2);
        Matrix4f matrix = stack.getLast().getMatrix();

        stack.translate(corner[0], corner[1] +.4f, corner[2]);
        stack.scale(scale, scale, scale);
        stack.rotate(mc.getRenderManager().getCameraOrientation());
        stack.rotate(Vector3f.ZP.rotationDegrees(180f));

        font.func_243247_a(text, offset, 0, color, false, matrix, buffer, false, opacity, lightLevel);
        stack.pop();
    }
    
    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightFor(LightType.BLOCK, pos);
        int sLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(bLight, sLight);
    }
    private void renderBook(RunicInterceptorTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, float scale) {
        matrixStackIn.push();
        matrixStackIn.translate(0.5D, 0.75D, 0.5D);
        float f = (float)tileEntityIn.ticks + partialTicks;
        matrixStackIn.translate(0.0D, (double)(0.1F + MathHelper.sin(f * 0.1F) * 0.01F), 0.0D);
  
        float f1;
        for(f1 = tileEntityIn.nextPageAngle - tileEntityIn.pageAngle; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
        }
  
        while(f1 < -(float)Math.PI) {
           f1 += ((float)Math.PI * 2F);
        }
  
        float f2 = tileEntityIn.pageAngle + f1 * partialTicks;
        matrixStackIn.rotate(Vector3f.YP.rotation(-f2));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(80.0F));
        matrixStackIn.scale(scale, scale, scale);
        float f3 = MathHelper.lerp(partialTicks, tileEntityIn.field_195524_g, tileEntityIn.field_195523_f);
        float f4 = MathHelper.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = MathHelper.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = MathHelper.lerp(partialTicks, tileEntityIn.pageTurningSpeed, tileEntityIn.nextPageTurningSpeed);
        this.modelBook.setBookState(f, MathHelper.clamp(f4, 0.0F, 1.0F), MathHelper.clamp(f5, 0.0F, 1.0F), f6);
        IVertexBuilder ivertexbuilder = TEXTURE_BOOK.getBuffer(bufferIn, RenderType::getEntitySolid);
        this.modelBook.renderAll(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
     }
}
