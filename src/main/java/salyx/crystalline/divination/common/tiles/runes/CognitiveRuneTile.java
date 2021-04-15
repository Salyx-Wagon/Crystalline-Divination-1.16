package salyx.crystalline.divination.common.tiles.runes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import salyx.crystalline.divination.common.blocks.runes.CognitiveRune;
import salyx.crystalline.divination.common.containers.CognitiveRuneContainer;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class CognitiveRuneTile extends LockableTileEntity implements ITickableTileEntity{

    public boolean getHasSentient(){
        return this.getTileData().getBoolean("hasSentient");
    }
    public void setHasSentient(boolean bool){
        this.getTileData().putBoolean("hasSentient", bool);
    }

    public BlockPos getSentient(){
        int[] blockPos = this.getTileData().getIntArray("sentientPos");
        BlockPos pos = new BlockPos(blockPos[0], blockPos[1], blockPos[2]);
        return pos;
    }

    public void setSentient(BlockPos pos){
        ArrayList<Integer> blockPos = new ArrayList<Integer>();
        blockPos.add(pos.getX()); blockPos.add(pos.getY()); blockPos.add(pos.getZ());
        this.getTileData().putIntArray("sentientPos", blockPos);
        this.setHasSentient(true);
    }

    public ArrayList<StorageRuneTile> getStorageRunes(){
        ArrayList<StorageRuneTile> storageRuneTiles = new ArrayList<StorageRuneTile>();
        if(this.getHasSentient()){
            if(this.getWorld().getTileEntity(this.getSentient()) instanceof SentientRuneTile){
                SentientRuneTile ste = (SentientRuneTile) this.getWorld().getTileEntity(this.getSentient());
                for(int i = 0; i < ste.getTileData().getList("storageRuneUUIDs", NBT.TAG_STRING).size(); i++){
                    BlockPos pos = new BlockPos(ste.getTileData().getIntArray(ste.getTileData().getList("storageRuneUUIDs", NBT.TAG_STRING).getString(i))[0],
                                                ste.getTileData().getIntArray(ste.getTileData().getList("storageRuneUUIDs", NBT.TAG_STRING).getString(i))[1],
                                                ste.getTileData().getIntArray(ste.getTileData().getList("storageRuneUUIDs", NBT.TAG_STRING).getString(i))[2]);
                    if(this.getWorld().getTileEntity(pos) instanceof StorageRuneTile){
                        storageRuneTiles.add((StorageRuneTile) this.getWorld().getTileEntity(pos));
                    }
                }
            }
        }

        return storageRuneTiles;
    }

    protected CognitiveRuneTile(TileEntityType<?> typeIn) {
        super(typeIn);
    }
    public CognitiveRuneTile() {
        this(TileEntityInit.COGNITIVE_RUNE_TILE_TYPE.get());
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public void tick() {
        if(this.getBlockState().get(CognitiveRune.CENTER) && !this.getWorld().isRemote()){
            
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
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        return compound;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("Cognitive_Rune");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new CognitiveRuneContainer(id, player, this);
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
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public ItemStack getStackInSlot(int index) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ItemStack decrStackSize(int index, int count) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }
    
}
