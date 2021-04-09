package salyx.crystalline.divination.common.tiles.runes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.common.blocks.runes.BaseRune;
import salyx.crystalline.divination.common.blocks.runes.Rune;
import salyx.crystalline.divination.common.containers.BaseRuneContainer;
import salyx.crystalline.divination.common.tiles.PedestalTile;
import salyx.crystalline.divination.common.tiles.RunicInterceptorTile;
import salyx.crystalline.divination.core.init.ItemInit;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class BaseRuneTile extends LockableLootTileEntity implements ITickableTileEntity{

    public boolean crafted;
    public boolean isCraftingBlock;
    public boolean isCraftingItem;
    private Rune craftingRune;
    private ItemStack craftingItem;
    public int craftingTier;
    public List<PedestalTile> pedestals;

    public static int slots = 5;

    public int tick;

    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    protected BaseRuneTile(TileEntityType<?> typeIn) {
        super(typeIn);
    }
    public BaseRuneTile() {
        this(TileEntityInit.BASE_RUNE_TILE_TYPE.get());
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
        return new TranslationTextComponent("container." + CrystalDiv.MOD_ID + ".base_rune");
    }
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new BaseRuneContainer(id, player, this);
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
    public void cancelCrafting(){
        isCraftingBlock = false;
        isCraftingItem = false;
        tick = 0;
        for(int i = 0; i < pedestals.size(); i++){
            if(pedestals.get(i) instanceof PedestalTile){
                pedestals.get(i).craftingRune = null;
                pedestals.get(i).isUsedForCrafting = false;
                
            }
        }
    }
    @SuppressWarnings("static-access")
    @Override
    public void tick() {  

        if(BaseRune.clickCooldown > 0) {
            BaseRune.clickCooldown -= 1; 
        }
        if(this.isCraftingBlock || this.isCraftingItem){this.tick ++;}
        this.getTileData().putInt("tick", this.tick);
        if(this.tick >= 200){
            if(this.craftingTier == 2){
                for(int p = 0; p<=3; p++){
                    PedestalTile pt = this.pedestals.get(p);
                    LazyOptional<IItemHandler> itemHandler = pt.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    itemHandler.ifPresent(h -> h.extractItem(0, 1, false));
                }
            }
            if(this.isCraftingBlock){
                this.crafted = true;
                this.isCraftingBlock = false;
                if(!this.getWorld().isRemote()){
                    ServerWorld world = (ServerWorld) this.getWorld();
                    world.spawnParticle(ParticleTypes.CLOUD, this.getPos().getX()+0.5, this.getPos().getY(), this.getPos().getZ()+0.5,
                    20, 0.5, 0, 0.5, 0.1);
                }
                if(this.checkInterceptor(2, ItemInit.BLANK_RUNIC_PARCHMENT.get().getDefaultInstance()) != null){
                    RunicInterceptorTile te = this.checkInterceptor(2, ItemInit.BLANK_RUNIC_PARCHMENT.get().getDefaultInstance());
                    LazyOptional<IItemHandler> itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    itemHandler.ifPresent(h -> h.extractItem(0, 1, false));
                    itemHandler.ifPresent(h -> h.insertItem(0, this.craftingItem, false));
                    this.getWorld().setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                    
                }
                else{
                    this.getWorld().setBlockState(this.getPos(), this.craftingRune.getDefaultState()
                    .with(this.craftingRune.FACING, this.getWorld().getBlockState(this.getPos()).get(this.craftingRune.FACING)));
                }
            }
            if(this.isCraftingItem){
                this.isCraftingItem = false;
                if(!this.getWorld().isRemote()){
                    ServerWorld world = (ServerWorld) this.getWorld();
                    world.spawnParticle(ParticleTypes.CLOUD, this.getPos().getX()+0.5, this.getPos().getY(), this.getPos().getZ()+0.5,
                    20, 0.5, 0, 0.5, 0.1);
                }
                LazyOptional<IItemHandler> itemHandler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                for(int e=4; e>-1; e--) {
                    int slot = e;
                    if(!this.getItem(e).isEmpty()) {
                        itemHandler.ifPresent(h -> h.extractItem(slot, 1, false));
                    
                    }   
                }
                if(this.checkInterceptor(2, ItemStack.EMPTY) != null){
                    RunicInterceptorTile te = this.checkInterceptor(2, Items.AIR.getDefaultInstance());
                    itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    itemHandler.ifPresent(h -> h.extractItem(0, 1, false));
                    itemHandler.ifPresent(h -> h.insertItem(0, this.craftingItem, false));
                    this.getWorld().setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                    
                }
                else{
                    itemHandler.ifPresent(h -> h.insertItem(0, this.craftingItem, false));
                    this.getWorld().setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                }
            }
        }
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
    public void craftRune(Rune rune, ItemStack item, int tier){
        this.tick = 0;
        this.craftingRune = rune;
        this.craftingItem = item;
        this.isCraftingBlock = true;
        this.craftingTier = tier;
    }
    public void craftItem(ItemStack item, int tier){
        this.tick = 0;
        this.craftingItem = item;
        this.isCraftingItem = true;
        this.craftingTier = tier;
    }
    @Nullable
    public List<PedestalTile> checkPedestals(int radius, int count){
        int numOfPed = 0;
        List<PedestalTile> pedestals = new ArrayList<PedestalTile>();
        for(int x = -radius; x <= radius; x++){
            for(int y = -radius; y <= radius; y++){
                for(int z = -radius; z <= radius; z++){
                    BlockPos pos = new BlockPos(this.getPos().getX()+x, this.getPos().getY()+y, this.getPos().getZ()+z);
                    if(this.world.getTileEntity(pos) instanceof PedestalTile){
                        PedestalTile te = (PedestalTile) this.world.getTileEntity(pos);
                        pedestals.add(te);
                        numOfPed ++;
                        if(numOfPed == count){break;}
                    }
                }
            }
        }
        if(numOfPed == count){return pedestals;}
        else{return null;}
    }
    @Nullable
    public RunicInterceptorTile checkInterceptor(int radius, ItemStack replaceItem){
        RunicInterceptorTile te = null;
        for(int x = -radius; x <= radius; x++){
            for(int y = -radius; y <= radius; y++){
                for(int z = -radius; z <= radius; z++){
                    BlockPos pos = new BlockPos(this.getPos().getX()+x, this.getPos().getY()+y, this.getPos().getZ()+z);
                    if(this.world.getTileEntity(pos) instanceof RunicInterceptorTile){
                        RunicInterceptorTile te1 = (RunicInterceptorTile) this.world.getTileEntity(pos);
                        if(replaceItem.isEmpty()){
                            if(te1.getItem().isEmpty()){
                            te = te1; break;}
                        }
                        else if(te1.getItem().getItem().getDefaultInstance().isItemEqual(replaceItem.getItem().getDefaultInstance())){
                            te = te1; break;}
                    }
                }
            }
        }
        if(te != null){return te;}
        return null;
    }
}
