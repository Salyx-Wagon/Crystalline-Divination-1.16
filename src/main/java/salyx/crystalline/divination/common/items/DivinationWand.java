package salyx.crystalline.divination.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import salyx.crystalline.divination.common.tiles.BaseRuneTile;
import salyx.crystalline.divination.common.tiles.ExportRuneTile;
import salyx.crystalline.divination.common.tiles.ImportRuneTile;
import salyx.crystalline.divination.common.tiles.StorageRuneTile;
import salyx.crystalline.divination.core.init.BlockInit;
import salyx.crystalline.divination.core.init.ItemInit;

public class DivinationWand extends Item{
    public int cooldown = 0;

    public DivinationWand(Properties properties) {
        super(properties.maxStackSize(1));
    }
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        
        if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof BaseRuneTile){
            BaseRuneTile bte = (BaseRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(bte.getItem(0).isItemEqualIgnoreDurability(Items.CHEST.getDefaultInstance()) &&
                bte.getItem(1).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(2).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(3).isItemEqualIgnoreDurability(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(4).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance())) {
                    bte.craftBlock(BlockInit.STORAGE_RUNE.get(), 1);
                }
            if(bte.getItem(0).isItemEqualIgnoreDurability(Items.GLASS_PANE.getDefaultInstance()) &&
                bte.getItem(1).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(2).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(3).isItemEqualIgnoreDurability(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(4).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance())) {
                    bte.craftItem(ItemInit.CRYSTALLINE_TABLET.get().getDefaultInstance(), 1);
                }
            if(bte.getItem(0).isItemEqualIgnoreDurability(Items.HOPPER.getDefaultInstance()) &&
                bte.getItem(1).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(2).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(3).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(4).isItemEqualIgnoreDurability(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance())) {
                    bte.craftBlock(BlockInit.EXPORT_RUNE.get(), 1);
                }
            if(bte.getItem(0).isItemEqualIgnoreDurability(Items.HOPPER.getDefaultInstance()) &&
                bte.getItem(1).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(2).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(3).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(4).isItemEqualIgnoreDurability(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance())) {
                    bte.craftBlock(BlockInit.IMPORT_RUNE.get(), 1);
                }
            // TIER 2 CRAFTING
            List<String> pedestalItems = new ArrayList<String>();
                if(bte.checkPedestals() != null){
                    pedestalItems.add(bte.checkPedestals().get(0).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(bte.checkPedestals().get(1).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(bte.checkPedestals().get(2).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(bte.checkPedestals().get(3).getItem().getItem().getDefaultInstance().toString());
                }
            if(bte.getItem(0).isItemEqualIgnoreDurability(Items.GRASS_BLOCK.getDefaultInstance()) &&
                bte.getItem(1).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(2).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(3).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance()) &&
                bte.getItem(4).isItemEqualIgnoreDurability(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance()) &&
                pedestalItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString())) {
                    bte.craftItem(ItemInit.PEDESTAL.get().getDefaultInstance(), 2);
                }
        }
        else if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof StorageRuneTile){
            CompoundNBT nbt;
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            nbt.putInt("X", context.getPos().getX());
            nbt.putInt("Y", context.getPos().getY());
            nbt.putInt("Z", context.getPos().getZ());
            context.getPlayer().getHeldItemMainhand().setTag(nbt);
        }
        else if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof ExportRuneTile){
            CompoundNBT nbt;
            ExportRuneTile te = (ExportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")){
                BlockPos sourcePos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
                if(sourcePos.withinDistance(te.getPos(), 64)) {
                    te.setSourceX(nbt.getInt("X"));
                    te.setSourceY(nbt.getInt("Y"));
                    te.setSourceZ(nbt.getInt("Z"));
                    te.setHasSource(true);
                }
            }
        }
        else if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof ImportRuneTile){
            CompoundNBT nbt;
            ImportRuneTile te = (ImportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")){
                BlockPos sourcePos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
                if(sourcePos.withinDistance(te.getPos(), 64)) {
                    te.setDestX(nbt.getInt("X"));
                    te.setDestY(nbt.getInt("Y"));
                    te.setDestZ(nbt.getInt("Z"));
                    te.setHasDest(true);
                }
            }
        }
        else if(context.getPlayer().isSneaking()){
            CompoundNBT nbt;
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(nbt.contains("X")) {nbt.remove("X");}
            if(nbt.contains("Y")) {nbt.remove("Y");}
            if(nbt.contains("Z")) {nbt.remove("Z");}
        }
        else if((context.getWorld().getTileEntity(context.getPos()) instanceof ExportRuneTile) && cooldown == 0 && context.getWorld().isRemote()){
            ExportRuneTile te = (ExportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemOffhand().isEmpty()){
                te.getTileData().putBoolean("clientHasFilter", false);
                te.getTileData().put("itemFilter1", ItemStack.EMPTY.serializeNBT());
            }
            else{
                te.getTileData().putBoolean("clientHasFilter", true);
                ItemStack filterItem = ItemStack.read(te.getTileData().getCompound("itemFilter1"));
                if(filterItem.isItemEqual(context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance())){
                    te.getTileData().putBoolean("clientIsWhitelist", !te.getTileData().getBoolean("clientIsWhitelist"));
                }else{
                    te.getTileData().put("itemFilter1", context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance().serializeNBT());
                }
            }
            
        }
        else if((context.getWorld().getTileEntity(context.getPos()) instanceof ImportRuneTile) && cooldown == 0 && context.getWorld().isRemote()){
            ImportRuneTile te = (ImportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemOffhand().isEmpty()){
                te.getTileData().putBoolean("clientHasFilter", false);
                te.getTileData().put("itemFilter1", ItemStack.EMPTY.serializeNBT());
            }
            else{
                te.getTileData().putBoolean("clientHasFilter", true);
                ItemStack filterItem = ItemStack.read(te.getTileData().getCompound("itemFilter1"));
                if(filterItem.isItemEqual(context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance())){
                    te.getTileData().putBoolean("clientIsWhitelist", !te.getTileData().getBoolean("clientIsWhitelist"));
                }else{
                    te.getTileData().put("itemFilter1", context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance().serializeNBT());
                }
            }
            
        }

        return super.onItemUse(context);
    }
}
