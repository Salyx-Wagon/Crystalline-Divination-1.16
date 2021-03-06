package salyx.crystalline.divination.common.tiles.runes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.common.containers.StorageRuneContainer;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class StorageRuneTile extends LockableLootTileEntity implements ITickableTileEntity{
    public static int slots = 27;

    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    protected StorageRuneTile(TileEntityType<?> typeIn) {
        super(typeIn);
    }
    public StorageRuneTile() {
        this(TileEntityInit.STORAGE_RUNE_TILE_TYPE.get());
    }
    @Override
    public int getSizeInventory() {
        return slots;
    }
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public ItemStack getItem(int index) {
        return this.items.get(index);
    }
    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + CrystalDiv.MOD_ID + ".storage_rune");
    }
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new StorageRuneContainer(id, player, this);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
        }
        return compound;
    }
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if(!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
    }
    @Override
    public void tick() {
        this.world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 0);
    }
    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }

    public void setSentient(BlockPos pos){
        if(!this.getTileData().getBoolean("hasSentient")){
            List<Integer> posList = new ArrayList<Integer>();
            posList.add(pos.getX());posList.add(pos.getY());posList.add(pos.getZ());
            this.getTileData().putIntArray("sentientPos", posList);
            this.getTileData().putBoolean("hasSentient", true);
            if(this.world.getTileEntity(pos) instanceof SentientRuneTile){
                SentientRuneTile te = (SentientRuneTile) this.world.getTileEntity(pos);
                te.addRune(this.pos, this.getTileData().getUniqueId("UUID"), "storage");
                
            }
        }
    }
    public BlockPos getSentient(){
        BlockPos pos = new BlockPos(this.getTileData().getIntArray("sentientPos")[0],
        this.getTileData().getIntArray("sentientPos")[1], this.getTileData().getIntArray("sentientPos")[2]);
        return pos;
    }
}
