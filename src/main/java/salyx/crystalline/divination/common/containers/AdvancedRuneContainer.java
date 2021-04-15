package salyx.crystalline.divination.common.containers;

import java.util.Objects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import salyx.crystalline.divination.common.tiles.runes.AdvancedRuneTile;
import salyx.crystalline.divination.core.init.ContainerTypeInit;

public class AdvancedRuneContainer extends Container{

    public final AdvancedRuneTile te;

    public AdvancedRuneContainer(final int windowId, final PlayerInventory playerInv, final AdvancedRuneTile te) {
        super(ContainerTypeInit.ADVANCED_RUNE_CONTAINER_TYPE.get(), windowId);
        this.te = te;
        
        // Tile Entity
        //this.addSlot(new Slot((IInventory) te, 0, 80, 35));
        for(int col = 0; col < 5; col ++) {
            this.addSlot(new Slot((IInventory) te, col, 44+col*18, 35));
        }

        // Main Player Inventory
        for(int row = 0; row < 3; row ++) {
            for(int col = 0; col < 9; col ++) {
                this.addSlot(new Slot(playerInv, col+row*9+9, 8+col*18, 166-(4-row)*18-10));
            }
        }
        // Player Hotbar
        for(int col = 0; col < 9; col ++) {
            this.addSlot(new Slot(playerInv, col, 8+col*18, 142));
        }
    }

    public AdvancedRuneContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowId, playerInv, getTileEntity(playerInv, data));
    }

    private static AdvancedRuneTile getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.world.getTileEntity(data.readBlockPos());
        if(te instanceof AdvancedRuneTile) {
            return (AdvancedRuneTile) te;
        }
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
            if(index < AdvancedRuneTile.slots && !this.mergeItemStack(stack1, AdvancedRuneTile.slots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if(!this.mergeItemStack(stack1, 0, AdvancedRuneTile.slots, false)) {
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
}
