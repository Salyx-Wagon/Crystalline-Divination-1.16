package salyx.crystalline.divination.common.blocks.runes;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import salyx.crystalline.divination.common.tiles.runes.SentientRuneTile;
import salyx.crystalline.divination.common.tiles.runes.StorageRuneTile;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class StorageRune extends Rune{
    public StorageRune(Properties properties) {
        super(properties);
        runCalculation(Block.makeCuboidShape(0, 0, 0, 16, 16, 1));
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if(worldIn.getTileEntity(pos) instanceof StorageRuneTile){
            StorageRuneTile te = (StorageRuneTile) worldIn.getTileEntity(pos);
            te.getTileData().putUniqueId("UUID", UUID.randomUUID());
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()) {

            TileEntity te = worldIn.getTileEntity(pos);
            
            if(te instanceof StorageRuneTile) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (StorageRuneTile) te, pos);
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.STORAGE_RUNE_TILE_TYPE.get().create();
    }
    @SuppressWarnings( "deprecation" )
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!state.isIn(newState.getBlock())) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof StorageRuneTile) {
                StorageRuneTile te = (StorageRuneTile) tileEntity;
                if(te.getTileData().contains("sentientPos")){
                    if(worldIn.getTileEntity(te.getSentient()) instanceof SentientRuneTile){
                        SentientRuneTile ste = (SentientRuneTile) worldIn.getTileEntity(te.getSentient());
                        ste.preCheck = true;
                    }
                }
                InventoryHelper.dropInventoryItems(worldIn, pos, te);
            }

        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}
