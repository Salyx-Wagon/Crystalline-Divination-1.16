package salyx.crystalline.divination.core.network.message;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class SentientRenderMessage {

    public int storageAmt;
    public BlockPos pos;

    public SentientRenderMessage() {

    }

    public SentientRenderMessage(int storageAmt, BlockPos pos){
        this.storageAmt = storageAmt; this.pos = pos;
    }

    public static void encode(SentientRenderMessage message, PacketBuffer buffer){
        buffer.writeInt(message.storageAmt); buffer.writeBlockPos(message.pos);
    }

    public static SentientRenderMessage decode(PacketBuffer buffer){
        return new SentientRenderMessage(buffer.readInt(), buffer.readBlockPos());
    }

    public static void handle(SentientRenderMessage message, Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            
        });
        context.setPacketHandled(true);
    }
    
}
