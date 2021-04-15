package salyx.crystalline.divination.common.blocks.runes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import salyx.crystalline.divination.common.tiles.runes.AdvancedRuneTile;
import salyx.crystalline.divination.core.init.ItemInit;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class AdvancedRune extends Rune{
    public int clickCooldown;

    public static final BooleanProperty CENTER = BooleanProperty.create("center");
    
    public AdvancedRune(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CENTER, true));
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        this.createSides(state, pos, worldIn);
        if(worldIn.getTileEntity(pos) instanceof AdvancedRuneTile){
            AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
            te.getTileData().putUniqueId("UUID", UUID.randomUUID());
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(CENTER);
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.ADVANCED_RUNE_TILE_TYPE.get().create();
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult hit) {
        
        AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
        if(worldIn.isRemote()) {
            return ActionResultType.SUCCESS;
        }
        if((!worldIn.isRemote()) && te instanceof AdvancedRuneTile && state.get(AdvancedRune.CENTER)) {
            //worldIn.getServer().getWorld(World.OVERWORLD).setDayTime(worldIn.getDayTime()+10);
            //TileEntity te = worldIn.getTileEntity(pos);
            LazyOptional<IItemHandler> itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if(this.clickCooldown == 0 && !player.getHeldItemMainhand().isItemEqual(ItemInit.DIVINATION_WAND.get().getDefaultInstance()) && te.tick == 0) {
                if(player.isSneaking() && player.getHeldItemMainhand().isEmpty()) {
                    for(int e=4; e>-1; e--) {
                        int slot = e;
                        if(!te.getItem(e).isEmpty()) {
                            itemHandler.ifPresent(h -> player.addItemStackToInventory(h.extractItem(slot, 1, false)));
                            break;
                        
                        }   
                    }
                }
                else if(!player.isSneaking() && !player.getHeldItemMainhand().isEmpty()){
                    for(int e=0; e<5; e++) {
                        int slot = e;
                        if(te.getItem(e).isEmpty()) {
                            itemHandler.ifPresent(h -> h.insertItem(slot, player.getHeldItemMainhand().copy(), false));
                            te.getItem(e).setCount(1);
                            player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount()-1);
                            break;
                        
                        }   
                    }
                }
                else if(player.getHeldItemMainhand().isEmpty() && state.get(AdvancedRune.CENTER)){
                    NetworkHooks.openGui((ServerPlayerEntity) player, (AdvancedRuneTile) te, pos);
                }
                this.clickCooldown += 20;
                te.markDirty();   
            }
            else if(player.getHeldItemMainhand().isItemEqual(ItemInit.DIVINATION_WAND.get().getDefaultInstance())) {
            }
        }
        worldIn.notifyBlockUpdate(pos, this.getDefaultState(), state, 0);
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
        
        if(!state.isIn(newState.getBlock()) && !worldIn.isRemote()){
            if(state.get(AdvancedRune.CENTER)){
                if(!te.crafted){
                    System.out.println(te.crafted);
                    InventoryHelper.dropInventoryItems(worldIn, pos, te);
                }
                
                for(int p = 0; p < te.getSides().size(); p++){
                    worldIn.destroyBlock(te.getSides().get(p), false);
                }
                worldIn.removeTileEntity(pos);
            }
            if(!state.get(AdvancedRune.CENTER)){
                worldIn.destroyBlock(te.getCenter(), false);
                worldIn.removeTileEntity(pos);
            }
        }
    }
    public void createSides(BlockState state, BlockPos pos, World worldIn){
        if(state.get(AdvancedRune.CENTER) == true){
            boolean canceled = false;
            if(state.get(AdvancedRune.FACING) == Direction.UP || state.get(AdvancedRune.FACING) == Direction.DOWN){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.north());sidePos.add(pos.east());sidePos.add(pos.south());sidePos.add(pos.west());
                sidePos.add(pos.north().east());sidePos.add(pos.east().south());
                sidePos.add(pos.south().west());sidePos.add(pos.west().north());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(AdvancedRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof AdvancedRuneTile){
                            AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof AdvancedRuneTile){
                    AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            else if(state.get(AdvancedRune.FACING) == Direction.NORTH || state.get(AdvancedRune.FACING) == Direction.SOUTH){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.up());sidePos.add(pos.east());sidePos.add(pos.down());sidePos.add(pos.west());
                sidePos.add(pos.up().east());sidePos.add(pos.east().down());
                sidePos.add(pos.down().west());sidePos.add(pos.west().up());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(AdvancedRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof AdvancedRuneTile){
                            AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof AdvancedRuneTile){
                    AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            else if(state.get(AdvancedRune.FACING) == Direction.EAST || state.get(AdvancedRune.FACING) == Direction.WEST){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.north());sidePos.add(pos.up());sidePos.add(pos.south());sidePos.add(pos.down());
                sidePos.add(pos.north().up());sidePos.add(pos.up().south());
                sidePos.add(pos.south().down());sidePos.add(pos.down().north());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(AdvancedRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof AdvancedRuneTile){
                            AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof AdvancedRuneTile){
                    AdvancedRuneTile te = (AdvancedRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            if(canceled){
                worldIn.destroyBlock(pos, false);
            }
        }
    }
}
