package salyx.crystalline.divination.core.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.core.network.message.SentientRenderMessage;

public class CrystalDivNetwork {
    
    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(CrystalDiv.MOD_ID, "network"),
                                                                                                            () -> NETWORK_VERSION,
                                                                                        version -> version.equals(NETWORK_VERSION),
                                                                                        version -> version.equals(NETWORK_VERSION));
    
    public static void init() {
        
        CHANNEL.registerMessage(0, SentientRenderMessage.class, SentientRenderMessage::encode, SentientRenderMessage::decode, SentientRenderMessage::handle);
        
    }
}
