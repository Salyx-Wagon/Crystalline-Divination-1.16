package salyx.crystalline.divination.common.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class CustomItemSlot extends Slot{

    public CustomItemSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
