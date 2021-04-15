package salyx.crystalline.divination.common.tiles.runes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import salyx.crystalline.divination.common.blocks.runes.SentientRune;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class SentientRuneTile extends LockableLootTileEntity implements ITickableTileEntity{

    public boolean check = false;
    public boolean preCheck = false;

    protected SentientRuneTile(TileEntityType<?> typeIn) {
        super(typeIn);
    }
    public SentientRuneTile() {
        this(TileEntityInit.SENTIENT_RUNE_TILE_TYPE.get());
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public void tick() {
        if(this.getBlockState().get(SentientRune.CENTER) && !this.getWorld().isRemote()){
            if(this.check){
                this.checkRunes("storage");
                this.check = false;
                
            }
            
            if(this.preCheck){
                this.check = true;
                this.preCheck = false;
            }
        }
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);

        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), this.getTileData());
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        
    }

    @Override
    protected ITextComponent getDefaultName() {
        return null;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        // TODO Auto-generated method stub
        super.read(state, nbt);;
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        return compound;
    }
    public void setCenterPos(BlockPos centerTile){
        List<Integer> pos = new ArrayList<Integer>();
        pos.add(centerTile.getX());
        pos.add(centerTile.getY());
        pos.add(centerTile.getZ());
        this.getTileData().putIntArray("center", pos);
    }
    public BlockPos getCenter(){
        int[] pos = this.getTileData().getIntArray("center");
        BlockPos pos1 = new BlockPos(pos[0], pos[1], pos[2]);
        return pos1;
    }
    public void setSides(List<BlockPos> sideRuneList){
        for(int sideRune = 0; sideRune < sideRuneList.size(); sideRune++){
            List<Integer> pos = new ArrayList<Integer>();
            pos.add(sideRuneList.get(sideRune).getX());
            pos.add(sideRuneList.get(sideRune).getY());
            pos.add(sideRuneList.get(sideRune).getZ());
            this.getTileData().putIntArray("side" + sideRune, pos);
        }
    }
    public List<BlockPos> getSides(){
        List<BlockPos> sides = new ArrayList<BlockPos>();
        for(int sideRune = 0; sideRune < 8; sideRune++){
            if(this.getTileData().contains("side" + sideRune)){
                sides.add(new BlockPos(this.getTileData().getIntArray("side" + sideRune)[0],
                                        this.getTileData().getIntArray("side" + sideRune)[1],
                                        this.getTileData().getIntArray("side" + sideRune)[2]));
            }
            else{break;}
        }
        return sides;
    }
    public void addRune(BlockPos runePos, UUID uuid, String type){
        ArrayList<Integer> pos = new ArrayList<Integer>();
        pos.add(runePos.getX());pos.add(runePos.getY());pos.add(runePos.getZ());
        this.getTileData().putIntArray(uuid.toString(), pos);
        
        ListNBT list = this.getTileData().getList(type + "RuneUUIDs", NBT.TAG_STRING);
        list.add(StringNBT.valueOf(uuid.toString()));
        this.getTileData().put(type + "RuneUUIDs", list);
        
    }
    public void delRune(UUID uuid, String type){
        ArrayList<String> UUIDs = new ArrayList<String>();
        ListNBT list = this.getTileData().getList(type + "RuneUUIDs", NBT.TAG_STRING);
        for(int index = 0; index < list.size(); index++) {
            UUIDs.add(list.getString(index));
        }
        if(UUIDs.contains(uuid.toString())){
            UUIDs.remove(uuid.toString());
            this.getTileData().remove(uuid.toString());
            
        }
        ListNBT newList = new ListNBT();
        for(int index = 0; index < UUIDs.size(); index ++){
            newList.add(StringNBT.valueOf(UUIDs.get(index)));
        }
        this.getTileData().put(type + "RuneUUIDs", newList);
        
    }
    public void checkRunes(String type){
        ArrayList<String> UUIDs = new ArrayList<String>();
        ListNBT list = this.getTileData().getList(type + "RuneUUIDs", NBT.TAG_STRING);
        for(int index = 0; index < list.size(); index++) {
            UUIDs.add(list.getString(index));
        }
        for(int u = 0; u < UUIDs.size(); u++){
            int posArray[] = this.getTileData().getIntArray(UUIDs.get(u));
            BlockPos pos = new BlockPos(posArray[0], posArray[1], posArray[2]);
            if(!(this.world.getTileEntity(pos) instanceof StorageRuneTile)){
                this.delRune(UUID.fromString(UUIDs.get(u)), type);
            }
        }
        
    
    }
    public int runeAmount(String type){
        return this.getTileData().getList(type + "RuneUUIDs", NBT.TAG_STRING).size();
    }
}
