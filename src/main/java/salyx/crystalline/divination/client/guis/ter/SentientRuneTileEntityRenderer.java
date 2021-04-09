package salyx.crystalline.divination.client.guis.ter;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import salyx.crystalline.divination.common.blocks.runes.SentientRune;
import salyx.crystalline.divination.common.tiles.runes.SentientRuneTile;
import salyx.crystalline.divination.core.init.ItemInit;

public class SentientRuneTileEntityRenderer extends TileEntityRenderer<SentientRuneTile>{
    private Minecraft mc = Minecraft.getInstance();

    public static float r = 0;

    public SentientRuneTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(SentientRuneTile te, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ClientPlayerEntity player = mc.player;
        int lightLevel = getLightLevel(te.getWorld(), te.getPos());
        int redstoneLevel = te.getWorld().getRedstonePowerFromNeighbors(te.getPos());
        double d = 1;
        if(!te.getPos().withinDistance(player.getPosition(), 8)){
            if(!te.getPos().withinDistance(player.getPosition(), 16)){
                d = 0;
            }
            else{d = 65/(te.getPos().distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), true)-8);}
            if(d>1){d = 1;}
        }
        float r1 = (float) Math.sin((r*Math.PI)/360)*6;
        float r2 = (float) Math.sin(((r+900)*Math.PI)/360)*6;

        int storageRunes = te.getTileData().getList("storageRuneUUIDs", NBT.TAG_STRING).size();

        if(d>0 && te.getBlockState().get(SentientRune.CENTER)){
            if(te.getBlockState().toString().contains("down") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0, 0.5d},
                Vector3f.YP.rotationDegrees(-r/10), Vector3f.XP.rotationDegrees(r1), Vector3f.ZP.rotationDegrees(r2),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (6*d));
                
                for(double h = 0; h < storageRunes; h++){
                    renderItem(ItemInit.STORAGE_RUNE.get().getDefaultInstance(), new double[] {0.5d, 1.2+((h/storageRunes)), 0.5d},
                    Vector3f.YP.rotationDegrees(r/10), Vector3f.XP.rotationDegrees(0), Vector3f.ZP.rotationDegrees(0),
                    matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                }
            }
            
            if(te.getBlockState().toString().contains("up") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.15, 0.5d},
                Vector3f.YP.rotationDegrees(-r/10), Vector3f.XP.rotationDegrees(r1), Vector3f.ZP.rotationDegrees(r2),
                matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (6*d));
                
                for(double h = 0; h < storageRunes; h++){
                    renderItem(ItemInit.STORAGE_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.55-((h/storageRunes)), 0.5d},
                    Vector3f.YP.rotationDegrees(r/10), Vector3f.XP.rotationDegrees(0), Vector3f.ZP.rotationDegrees(0),
                    matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                }
            }
            if(te.getBlockState().toString().contains("north") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.5d, 0.5d},
                Vector3f.ZP.rotationDegrees(r/10), Vector3f.XP.rotationDegrees(90), Vector3f.XP.rotationDegrees(0),
            matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (6*d));
                
            }
            if(te.getBlockState().toString().contains("south") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.5d, 0.2d+(1)},
                Vector3f.ZP.rotationDegrees(-r/10), Vector3f.XP.rotationDegrees(90), Vector3f.XP.rotationDegrees(0),
            matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                
            }
            if(te.getBlockState().toString().contains("east") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.5d, 0.5d, 0.5d},
                Vector3f.XP.rotationDegrees(r/10), Vector3f.ZP.rotationDegrees(90), Vector3f.XP.rotationDegrees(0),
            matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                
            }
            if(te.getBlockState().toString().contains("west") && redstoneLevel == 0) {
                renderItem(ItemInit.SENTIENT_RUNE.get().getDefaultInstance(), new double[] {0.81d-(1), 0.5d, 0.5d},
                Vector3f.XP.rotationDegrees(-r/10), Vector3f.ZP.rotationDegrees(90), Vector3f.XP.rotationDegrees(0),
            matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, (float) (2*d));
                
            }
        }       
    }

    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation1, Quaternion rotation2, Quaternion rotation3, MatrixStack matrixStack,
    IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {

        matrixStack.push();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.rotate(rotation1);
        matrixStack.rotate(rotation2);
        matrixStack.rotate(rotation3);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        matrixStack.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightFor(LightType.BLOCK, pos);
        int sLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(bLight, sLight);
    }
}
