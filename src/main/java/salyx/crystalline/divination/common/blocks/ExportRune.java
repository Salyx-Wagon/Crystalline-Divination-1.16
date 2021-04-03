package salyx.crystalline.divination.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import salyx.crystalline.divination.common.items.DivinationWand;
import salyx.crystalline.divination.common.tiles.ExportRuneTile;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class ExportRune extends Rune{
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public ExportRune(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(ENABLED, Boolean.valueOf(true)));
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.EXPORT_RUNE_TILE_TYPE.get().create();
    }
    @SuppressWarnings( "deprecation" )
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!state.isIn(newState.getBlock())) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof ExportRuneTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (ExportRuneTile)tileEntity);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(ENABLED, true);
    }
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ENABLED);
    }
    private void updateState(World worldIn, BlockPos pos, BlockState state) {
        boolean flag = !worldIn.isBlockPowered(pos);
        if (flag != state.get(ENABLED)) {
           worldIn.setBlockState(pos, state.with(ENABLED, Boolean.valueOf(flag)), 4);
        }
  
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.isIn(state.getBlock())) {
            this.updateState(worldIn, pos, state);
        }
    }
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
            boolean isMoving) {
        this.updateState(worldIn, pos, state);
    }
    @SuppressWarnings( "deprecation" )
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult hit) {
        if((player.getHeldItemMainhand().getItem() instanceof DivinationWand) && !worldIn.isRemote()){
            DivinationWand wand = (DivinationWand) player.getHeldItemMainhand().getItem();
            ExportRuneTile te = (ExportRuneTile) worldIn.getTileEntity(pos);
            if(player.getHeldItemOffhand().isEmpty()){
                te.getTileData().putBoolean("hasFilter", false);
                te.getTileData().put("itemFilter", ItemStack.EMPTY.serializeNBT());
            }
            else if(wand.cooldown == 0){
                te.getTileData().putBoolean("hasFilter", true);
                ItemStack offhand = player.getHeldItemOffhand().getItem().getDefaultInstance();
                ItemStack filterItem = ItemStack.read(te.getTileData().getCompound("itemFilter"));
                if(offhand.isItemEqual(filterItem)){
                    te.getTileData().putBoolean("isWhiteList", !te.getTileData().getBoolean("isWhiteList"));
                }else{
                    te.getTileData().put("itemFilter", offhand.serializeNBT());
                }
                wand.cooldown += 5;
            }
            
        }

        
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
