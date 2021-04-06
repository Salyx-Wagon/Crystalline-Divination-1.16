package salyx.crystalline.divination.common.blocks.runes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import salyx.crystalline.divination.common.tiles.runes.SentientRuneTile;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class SentientRune extends Rune{

    public static final BooleanProperty CENTER = BooleanProperty.create("center");
    
    public SentientRune(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CENTER, true));
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        this.createSides(state, pos, worldIn);
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
        return TileEntityInit.SENTIENT_RUNE_TILE_TYPE.get().create();
    }
    @SuppressWarnings( "deprecation" )
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(pos);
        if(!state.isIn(newState.getBlock())) {
            if(state.get(SentientRune.CENTER)){
                for(int p = 0; p < te.getSides().size(); p++){
                    worldIn.destroyBlock(te.getSides().get(p), false);
                }
            }
            if(!state.get(SentientRune.CENTER)){
                worldIn.destroyBlock(te.getCenter(), false);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
    public void createSides(BlockState state, BlockPos pos, World worldIn){
        if(state.get(SentientRune.CENTER) == true){
            boolean canceled = false;
            if(state.get(SentientRune.FACING) == Direction.UP || state.get(SentientRune.FACING) == Direction.DOWN){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.north());sidePos.add(pos.east());sidePos.add(pos.south());sidePos.add(pos.west());
                sidePos.add(pos.north().east());sidePos.add(pos.east().south());
                sidePos.add(pos.south().west());sidePos.add(pos.west().north());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(SentientRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof SentientRuneTile){
                            SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof SentientRuneTile){
                    SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            else if(state.get(SentientRune.FACING) == Direction.NORTH || state.get(SentientRune.FACING) == Direction.SOUTH){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.up());sidePos.add(pos.east());sidePos.add(pos.down());sidePos.add(pos.west());
                sidePos.add(pos.up().east());sidePos.add(pos.east().down());
                sidePos.add(pos.down().west());sidePos.add(pos.west().up());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(SentientRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof SentientRuneTile){
                            SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof SentientRuneTile){
                    SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            else if(state.get(SentientRune.FACING) == Direction.EAST || state.get(SentientRune.FACING) == Direction.WEST){
                List<BlockPos> sidePos = new ArrayList<BlockPos>();
                sidePos.add(pos.north());sidePos.add(pos.up());sidePos.add(pos.south());sidePos.add(pos.down());
                sidePos.add(pos.north().up());sidePos.add(pos.up().south());
                sidePos.add(pos.south().down());sidePos.add(pos.down().north());
                List<BlockPos> sidePos1 = new ArrayList<BlockPos>();
                for(int pos1 = 0; pos1 < sidePos.size(); pos1++){
                    if(worldIn.getBlockState(sidePos.get(pos1)).isIn(Blocks.AIR) && !canceled){
                        worldIn.setBlockState(sidePos.get(pos1), state.with(SentientRune.CENTER, false));
                        if(worldIn.getTileEntity(sidePos.get(pos1)) instanceof SentientRuneTile){
                            SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(sidePos.get(pos1));
                            te.setCenterPos(pos);
                            sidePos1.add(sidePos.get(pos1));
                        }
                    }
                    else{
                        canceled = true;
                    }
                }
                if(worldIn.getTileEntity(pos) instanceof SentientRuneTile){
                    SentientRuneTile te = (SentientRuneTile) worldIn.getTileEntity(pos);
                    te.setSides(sidePos1);
                }
            }
            if(canceled){
                worldIn.destroyBlock(pos, false);
            }
        }
    }
}
