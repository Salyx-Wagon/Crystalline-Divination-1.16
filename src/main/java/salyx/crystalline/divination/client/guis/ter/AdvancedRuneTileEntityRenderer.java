package salyx.crystalline.divination.client.guis.ter;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import salyx.crystalline.divination.common.tiles.runes.AdvancedRuneTile;
import salyx.crystalline.divination.core.init.ItemInit;

public class AdvancedRuneTileEntityRenderer extends TileEntityRenderer<AdvancedRuneTile> {

    public static double r = 0;

    private Minecraft mc = Minecraft.getInstance();

    public AdvancedRuneTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(AdvancedRuneTile te, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(te.getItem(0).equals(ItemStack.EMPTY)) 
            return; 

            double r1 = (Math.sin((Math.PI * (r/1000)) + (0.5 * Math.PI))/3.5)+0.5;
            double r2 = (Math.sin((Math.PI * (((r+500)/1000))) + (0.5 * Math.PI))/3.5)+0.5;
            double r3 = (Math.sin((Math.PI * (((r+1000)/1000))) + (0.5 * Math.PI))/3.5)+0.5;
            double r4 = (Math.sin((Math.PI * (((r+1500)/1000))) + (0.5 * Math.PI))/3.5)+0.5;
            float r5 = (float) (r/5.55555);
            
            ClientPlayerEntity player = mc.player;
            int lightLevel = getLightLevel(te.getWorld(), te.getPos());
            double d = 1;
            int tick = te.getTileData().getInt("tick");
            if(!te.getPos().withinDistance(player.getPosition(), 8)){
                if(!te.getPos().withinDistance(player.getPosition(), 16)){
                    d = 0;
                }
                
                else{d = 52/(te.getPos().distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), true)-8);}
            }
            if(d>0)
            {if(te.getBlockState().toString().contains("down")) {
                renderItem(te.getItem(0), new double[] {0.5d, 0.3d, 0.5d}, Vector3f.YP.rotationDegrees(-r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {r1, 0.3d, r2}, Vector3f.YP.rotationDegrees(r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {r2, 0.3d, r3}, Vector3f.YP.rotationDegrees(r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {r3, 0.3d, r4}, Vector3f.YP.rotationDegrees(r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {r4, 0.3d, r1}, Vector3f.YP.rotationDegrees(r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                if(tick>0 && tick <= 50){
                    double h = ((double)tick)/100;
                    renderItem(ItemInit.ADVANCED_RUNE.get().getDefaultInstance(), new double[] {0.5d, h, 0.5d}, Vector3f.YP.rotationDegrees(-r5), Vector3f.YP.rotationDegrees(0),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                } else if(tick>50 && tick <= 200) {
                    float q = (((float)tick)-50)/15;
                    float r0 = (((float)tick)-50)*q;
                    float s = (150-(((float)tick)-50))/75;
                    renderItem(ItemInit.ADVANCED_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.5, 0.5d},
                    Vector3f.YP.rotationDegrees(-r5), Vector3f.XP.rotationDegrees(r0), Vector3f.ZP.rotationDegrees(r0),
                    matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (s*d));
                }
            }
            if(te.getBlockState().toString().contains("up")) {
                renderItem(te.getItem(0), new double[] {0.5d, 1-0.3d, 0.5d}, Vector3f.XP.rotationDegrees(180), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {r1, 1-0.3d, r2}, Vector3f.XP.rotationDegrees(180), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {r2, 1-0.3d, r3}, Vector3f.XP.rotationDegrees(180), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {r3, 1-0.3d, r4}, Vector3f.XP.rotationDegrees(180), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {r4, 1-0.3d, r1}, Vector3f.XP.rotationDegrees(180), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
            }
            if(te.getBlockState().toString().contains("north")) {
                renderItem(te.getItem(0), new double[] {0.5d, 0.5d, 0.3d}, Vector3f.XP.rotationDegrees(90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {r1, r2, 0.3d}, Vector3f.XP.rotationDegrees(90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {r2, r3, 0.3d}, Vector3f.XP.rotationDegrees(90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {r3, r4, 0.3d}, Vector3f.XP.rotationDegrees(90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {r4, r1, 0.3d}, Vector3f.XP.rotationDegrees(90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
            }
            if(te.getBlockState().toString().contains("south")) {
                renderItem(te.getItem(0), new double[] {0.5d, 0.5d, 1-0.3d}, Vector3f.XP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {r1, r2, 1-0.3d}, Vector3f.XP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {r2, r3, 1-0.3d}, Vector3f.XP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {r3, r4, 1-0.3d}, Vector3f.XP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {r4, r1, 1-0.3d}, Vector3f.XP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
            }
            if(te.getBlockState().toString().contains("east")) {
                renderItem(te.getItem(0), new double[] {1-0.3d, 0.5, 0.5}, Vector3f.ZP.rotationDegrees(90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {1-0.3d, r1, r2}, Vector3f.ZP.rotationDegrees(90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {1-0.3d, r2, r3}, Vector3f.ZP.rotationDegrees(90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {1-0.3d, r3, r4}, Vector3f.ZP.rotationDegrees(90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {1-0.3d, r4, r1}, Vector3f.ZP.rotationDegrees(90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
            }
            if(te.getBlockState().toString().contains("west")) {
                renderItem(te.getItem(0), new double[] {0.3d, 0.5d, 0.5d}, Vector3f.ZP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.8*d));
                renderItem(te.getItem(1), new double[] {0.3d, r1, r2}, Vector3f.ZP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(2), new double[] {0.3d, r2, r3}, Vector3f.ZP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(3), new double[] {0.3d, r3, r4}, Vector3f.ZP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
                renderItem(te.getItem(4), new double[] {0.3d, r4, r1}, Vector3f.ZP.rotationDegrees(-90), Vector3f.YP.rotationDegrees(-r5),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (0.6*d));
            }}
            
            ITextComponent label = te.getItem(0).hasDisplayName() ? te.getItem(0).getDisplayName() :new TranslationTextComponent(te.getItem(0).getTranslationKey());
            if(player.getHeldItemMainhand().getItem() == ItemInit.ADVANCED_ITEM.get()) {
                renderLabel(matrixStackIn, bufferIn, lightLevel, new double[] {0.5d, 1.3d, 0.5d}, label , 0xffffff);   
            } 
                
    }

    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, Quaternion rotation2, MatrixStack matrixStack,
    IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {

        matrixStack.push();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.rotate(rotation);
        matrixStack.rotate(rotation2);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        matrixStack.pop();
    }
    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, Quaternion rotation2, Quaternion rotation3, MatrixStack matrixStack,
    IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {

        matrixStack.push();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.rotate(rotation);
        matrixStack.rotate(rotation2);
        matrixStack.rotate(rotation3);
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
}
