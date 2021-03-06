package salyx.crystalline.divination.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.client.guis.ter.AdvancedRuneTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.BaseRuneTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.ExportRuneTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.ImportRuneTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.PedestalTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.RunicInterceptorTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.SentientRuneTileEntityRenderer;
import salyx.crystalline.divination.client.guis.ter.StorageRuneTileEntityRenderer;

@EventBusSubscriber(modid = CrystalDiv.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    
    @SubscribeEvent
    public static void renderHand(final RenderHandEvent event) {
        Minecraft.getInstance().setDefaultMinecraftTitle();
        event.getMatrixStack().push();

    }
    @SubscribeEvent
    public static void tick(final TickEvent event) {
        PedestalTileEntityRenderer.r ++;
        if(PedestalTileEntityRenderer.r >= 3600) {
            PedestalTileEntityRenderer.r = 0;
        }
        RunicInterceptorTileEntityRenderer.r ++;
        if(RunicInterceptorTileEntityRenderer.r >= 3600) {
            RunicInterceptorTileEntityRenderer.r = 0;
        }
        StorageRuneTileEntityRenderer.r ++;
        if(StorageRuneTileEntityRenderer.r >= 3600) {
            StorageRuneTileEntityRenderer.r = 0;
        }
        BaseRuneTileEntityRenderer.r ++;
        if(BaseRuneTileEntityRenderer.r >= 2000) {
            BaseRuneTileEntityRenderer.r = 0;
        }
        AdvancedRuneTileEntityRenderer.r ++;
        if(AdvancedRuneTileEntityRenderer.r >= 2000) {
            AdvancedRuneTileEntityRenderer.r = 0;
        }
        ExportRuneTileEntityRenderer.r ++;
        if(ExportRuneTileEntityRenderer.r >= 2000) {
            ExportRuneTileEntityRenderer.r = 0;
        }
        --ImportRuneTileEntityRenderer.r;
        if(ImportRuneTileEntityRenderer.r <= 0) {
            ImportRuneTileEntityRenderer.r = 2000;
        }
        SentientRuneTileEntityRenderer.r += 0.5;
        if(SentientRuneTileEntityRenderer.r >= 3600) {
            SentientRuneTileEntityRenderer.r = 0;
        }
    }
    //@SubscribeEvent
    //public static void stitch(final TextureStitchEvent.Pre event){
    //    //event.addSprite(new ResourceLocation("crystaldiv:entities/default_runic_parchment"));
    //}
}
