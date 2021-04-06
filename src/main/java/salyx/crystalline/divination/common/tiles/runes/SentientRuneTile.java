package salyx.crystalline.divination.common.tiles.runes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class SentientRuneTile extends LockableLootTileEntity implements ITickableTileEntity{

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
    
}
