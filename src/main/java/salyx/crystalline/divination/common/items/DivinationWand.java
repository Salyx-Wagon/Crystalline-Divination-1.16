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
import net.minecraft.util.text.TranslationTextComponent;
import salyx.crystalline.divination.common.tiles.runes.BaseRuneTile;
import salyx.crystalline.divination.common.tiles.runes.ExportRuneTile;
import salyx.crystalline.divination.common.tiles.runes.ImportRuneTile;
import salyx.crystalline.divination.common.tiles.runes.SentientRuneTile;
import salyx.crystalline.divination.common.blocks.runes.SentientRune;
import salyx.crystalline.divination.common.tiles.PedestalTile;
import salyx.crystalline.divination.common.tiles.runes.StorageRuneTile;
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
            List<String> baseRuneItems = new ArrayList<String>();
            baseRuneItems.add(bte.getItem(1).getItem().getItem().getDefaultInstance().toString());
            baseRuneItems.add(bte.getItem(2).getItem().getItem().getDefaultInstance().toString());
            baseRuneItems.add(bte.getItem(3).getItem().getItem().getDefaultInstance().toString());
            baseRuneItems.add(bte.getItem(4).getItem().getItem().getDefaultInstance().toString());
            baseRuneItems.sort(null);
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(Items.CHEST.getDefaultInstance()) &&
                baseRuneItems.contains(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance().toString())) {
                    bte.craftRune(BlockInit.STORAGE_RUNE.get(), ItemInit.STORAGE_RUNIC_PARCHMENT.get().getDefaultInstance(), 1);
                }
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(Items.GLASS_PANE.getDefaultInstance()) &&
                baseRuneItems.contains(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.PYRO_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.contains(ItemInit.HYDRO_CRYSTAL.get().getDefaultInstance().toString())) {
                    bte.craftItem(ItemInit.CRYSTALLINE_TABLET.get().getDefaultInstance(), 1);
                }
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(Items.HOPPER.getDefaultInstance()) &&
                baseRuneItems.get(0).equals(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(1).equals(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(2).equals(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(3).equals(ItemInit.SOLAR_CRYSTAL.get().getDefaultInstance().toString())) {
                    bte.craftRune(BlockInit.EXPORT_RUNE.get(), ItemInit.EXPORT_RUNIC_PARCHMENT.get().getDefaultInstance(), 1);
                }
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(Items.HOPPER.getDefaultInstance()) &&
                baseRuneItems.get(0).equals(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(1).equals(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(2).equals(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(3).equals(ItemInit.LUNAR_CRYSTAL.get().getDefaultInstance().toString())) {
                    bte.craftRune(BlockInit.IMPORT_RUNE.get(), ItemInit.IMPORT_RUNIC_PARCHMENT.get().getDefaultInstance(), 1);
                }
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(ItemInit.PURE_CRYSTAL_DUST.get().getDefaultInstance()) &&
                baseRuneItems.get(0).equals(Items.PAPER.getDefaultInstance().toString()) &&
                baseRuneItems.get(1).equals(Items.PAPER.getDefaultInstance().toString()) &&
                baseRuneItems.get(2).equals(Items.PAPER.getDefaultInstance().toString()) &&
                baseRuneItems.get(3).equals(Items.PAPER.getDefaultInstance().toString())) {
                    bte.craftRune(BlockInit.IMPORT_RUNE.get(), ItemInit.IMPORT_RUNIC_PARCHMENT.get().getDefaultInstance(), 1);
                }
            // TIER 2 CRAFTING
            List<PedestalTile> pedestals = bte.checkPedestals(2, 4);
            List<String> pedestalItems = new ArrayList<String>();
                if(bte.checkPedestals(2, 4) != null){
                    pedestalItems.add(pedestals.get(0).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(pedestals.get(1).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(pedestals.get(2).getItem().getItem().getDefaultInstance().toString());
                    pedestalItems.add(pedestals.get(3).getItem().getItem().getDefaultInstance().toString());
                }
            pedestalItems.sort(null);
            if(bte.getItem(0).getItem().getDefaultInstance().isItemEqualIgnoreDurability(ItemInit.PEDESTAL.get().getDefaultInstance()) &&
                baseRuneItems.get(0).equals(ItemInit.PURE_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(1).equals(ItemInit.PURE_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(2).equals(ItemInit.PURE_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                baseRuneItems.get(3).equals(ItemInit.PURE_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.SOLAR_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.LUNAR_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.PYRO_CRYSTAL_DUST.get().getDefaultInstance().toString()) &&
                pedestalItems.contains(ItemInit.HYDRO_CRYSTAL_DUST.get().getDefaultInstance().toString())) {
                    
                    for(int p = 0; p<4; p++){
                        pedestals.get(p).isUsedForCrafting = true;
                        pedestals.get(p).craftingRune = bte;
                    }
                    bte.pedestals = pedestals;
                    bte.craftItem(ItemInit.RUNIC_INTERCEPTOR.get().getDefaultInstance(), 2);
                }
        }
        else if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof StorageRuneTile && cooldown == 0){
            if(!context.getWorld().isRemote()){
                StorageRuneTile te = (StorageRuneTile) context.getWorld().getTileEntity(context.getPos());
            CompoundNBT nbt;
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(nbt.getString("sourceBlock") == "sentient"){
                BlockPos sourcePos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
                if(sourcePos.withinDistance(te.getPos(), 128)) {
                    te.setSentient(sourcePos);
                }
                else{
                    context.getPlayer().sendMessage(new TranslationTextComponent("Too Far Away"), context.getPlayer().getUniqueID());
                }
                
            }
            else{
                nbt.putString("sourceBlock", "storage");
                nbt.putInt("X", context.getPos().getX());
                nbt.putInt("Y", context.getPos().getY());
                nbt.putInt("Z", context.getPos().getZ());
                context.getPlayer().getHeldItemMainhand().setTag(nbt);
            }}
            
        }
        else if(context.getPlayer().isSneaking() && context.getWorld().getTileEntity(context.getPos()) instanceof SentientRuneTile){
            SentientRuneTile te = (SentientRuneTile) context.getWorld().getTileEntity(context.getPos());
            CompoundNBT nbt;
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(context.getWorld().getBlockState(context.getPos()).get(SentientRune.CENTER)){
                nbt.putString("sourceBlock", "sentient");
                nbt.putInt("X", context.getPos().getX());
                nbt.putInt("Y", context.getPos().getY());
                nbt.putInt("Z", context.getPos().getZ());
                context.getPlayer().getHeldItemMainhand().setTag(nbt);
            }
            else if(!context.getWorld().getBlockState(context.getPos()).get(SentientRune.CENTER)){
                nbt.putString("sourceBlock", "sentient");
                nbt.putInt("X", te.getCenter().getX());
                nbt.putInt("Y", te.getCenter().getY());
                nbt.putInt("Z", te.getCenter().getZ());
                context.getPlayer().getHeldItemMainhand().setTag(nbt);
            }
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
            if(nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z") && nbt.contains("sourceBlock")){
                if(nbt.getString("sourceBlock") == "storage"){
                    BlockPos sourcePos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
                    if(sourcePos.withinDistance(te.getPos(), 64)) {
                        te.setSourceX(nbt.getInt("X"));
                        te.setSourceY(nbt.getInt("Y"));
                        te.setSourceZ(nbt.getInt("Z"));
                        te.setHasSource(true);
                    }
                    else{
                        context.getPlayer().sendMessage(new TranslationTextComponent("Too Far Away"), context.getPlayer().getUniqueID());
                    }
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
            if(nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z") && nbt.contains("sourceBlock")){
                if(nbt.getString("sourceBlock") == "storage"){
                    BlockPos sourcePos = new BlockPos(nbt.getInt("X"), nbt.getInt("Y"), nbt.getInt("Z"));
                    if(sourcePos.withinDistance(te.getPos(), 64)) {
                        te.setDestX(nbt.getInt("X"));
                        te.setDestY(nbt.getInt("Y"));
                        te.setDestZ(nbt.getInt("Z"));
                        te.setHasDest(true);
                    }
                    else{
                        context.getPlayer().sendMessage(new TranslationTextComponent("Too Far Away"), context.getPlayer().getUniqueID());
                    }
                }
            }
        }
        else if(context.getPlayer().isSneaking() && cooldown == 0){
            CompoundNBT nbt;
            if(context.getPlayer().getHeldItemMainhand().hasTag()) {
                nbt = context.getPlayer().getHeldItemMainhand().getTag();
            }
            else {
                nbt = new CompoundNBT();
            }
            if(nbt.contains("sourceBlock")) {nbt.remove("sourceBlock");}
            if(nbt.contains("X")) {nbt.remove("X");}
            if(nbt.contains("Y")) {nbt.remove("Y");}
            if(nbt.contains("Z")) {nbt.remove("Z");}
        }
        else if((context.getWorld().getTileEntity(context.getPos()) instanceof ExportRuneTile) && cooldown == 0){
            ExportRuneTile te = (ExportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemOffhand().isEmpty()){
                te.getTileData().putBoolean("hasFilter", false);
                te.getTileData().put("itemFilter1", ItemStack.EMPTY.serializeNBT());
            }
            else{
                te.getTileData().putBoolean("hasFilter", true);
                ItemStack filterItem = ItemStack.read(te.getTileData().getCompound("itemFilter1"));
                if(filterItem.isItemEqual(context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance())){
                    te.getTileData().putBoolean("isWhitelist", !te.getTileData().getBoolean("isWhitelist"));
                }else{
                    te.getTileData().put("itemFilter1", context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance().serializeNBT());
                }
            }
            
        }
        else if((context.getWorld().getTileEntity(context.getPos()) instanceof ImportRuneTile) && cooldown == 0){
            ImportRuneTile te = (ImportRuneTile) context.getWorld().getTileEntity(context.getPos());
            if(context.getPlayer().getHeldItemOffhand().isEmpty()){
                te.getTileData().putBoolean("hasFilter", false);
                te.getTileData().put("itemFilter1", ItemStack.EMPTY.serializeNBT());
            }
            else{
                te.getTileData().putBoolean("hasFilter", true);
                ItemStack filterItem = ItemStack.read(te.getTileData().getCompound("itemFilter1"));
                if(filterItem.isItemEqual(context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance())){
                    te.getTileData().putBoolean("isWhitelist", !te.getTileData().getBoolean("isWhitelist"));
                }else{
                    te.getTileData().put("itemFilter1", context.getPlayer().getHeldItemOffhand().getItem().getDefaultInstance().serializeNBT());
                }
            }
            
        }
        return super.onItemUse(context);
    }
}
