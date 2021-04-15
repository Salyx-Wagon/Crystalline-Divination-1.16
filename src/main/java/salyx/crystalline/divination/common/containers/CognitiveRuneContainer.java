package salyx.crystalline.divination.common.containers;

import java.util.ArrayList;
import java.util.Objects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import salyx.crystalline.divination.common.tiles.runes.CognitiveRuneTile;
import salyx.crystalline.divination.common.tiles.runes.StorageRuneTile;
import salyx.crystalline.divination.core.init.ContainerTypeInit;

public class CognitiveRuneContainer extends Container{
    
    public final CognitiveRuneTile te;
    public int storageRune;
    public ArrayList<Slot> slots;
    
    public CognitiveRuneContainer(final int windowId, final PlayerInventory playerInv, final CognitiveRuneTile te) {
        super(ContainerTypeInit.COGNITIVE_RUNE_CONTAINER_TYPE.get(), windowId);
        this.te = te;
        this.storageRune = 0;
        this.slots = new ArrayList<Slot>();

        // Tile Entity
        for(int s = 0; s < te.getStorageRunes().size(); s++){
            StorageRuneTile ste = te.getStorageRunes().get(s);
            for(int row = 0; row < 3; row ++) {
                for(int col = 0; col < 9; col ++) {
                    this.addSlot(new Slot((IInventory) ste, col+row*9, 8+col*18, 99-(4-row)*18-10+(s*18*3)));
                    
                }
            }
        }

        // Main Player Inventory
        for(int row = 0; row < 3; row ++) {
            for(int col = 0; col < 9; col ++) {
                this.addSlot(new Slot(playerInv, col+row*9+9, 8+col*18, 220-(4-row)*18-10));
            }
        }
        // Player Hotbar
        for(int col = 0; col < 9; col ++) {
            this.addSlot(new Slot(playerInv, col, 8+col*18, 196));
        }
    }

    public CognitiveRuneContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowId, playerInv, getTileEntity(playerInv, data));
    }

    private static CognitiveRuneTile getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.world.getTileEntity(data.readBlockPos());
        
        if(te instanceof CognitiveRuneTile && te.getPos().withinDistance(playerInv.player.getPosition(), 128)) {
            return (CognitiveRuneTile) te;
        }
        ITextComponent m = new StringTextComponent("Too far away");
        playerInv.player.sendMessage(m, playerInv.player.getUniqueID());
        throw new IllegalStateException("Tile Entity is not correct");
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getPosition().withinDistance(te.getPos(), 128);
        
    }
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if(!this.te.getHasSentient()){
                return ItemStack.EMPTY;
            }
            if(this.te.getStorageRunes().size()<1){
                return ItemStack.EMPTY;
            }
            if(index < StorageRuneTile.slots && !this.mergeItemStack(stack1, StorageRuneTile.slots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if(!this.mergeItemStack(stack1, 0, StorageRuneTile.slots, false)) {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        //System.out.println(slotId);
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }
    @Override
    public Slot addSlot(Slot slotIn) {
        //System.out.println(slotIn.getSlotIndex());
        return super.addSlot(slotIn);
    }
    
}
