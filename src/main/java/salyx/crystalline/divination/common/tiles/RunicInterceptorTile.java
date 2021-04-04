package salyx.crystalline.divination.common.tiles;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.core.init.TileEntityInit;

public class RunicInterceptorTile extends LockableLootTileEntity implements ITickableTileEntity{
    public int ticks;
    public float field_195523_f;
    public float field_195524_g;
    public float field_195525_h;
    public float field_195526_i;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    public float nextPageAngle;
    public float pageAngle;
    public float field_195531_n;
    private static final Random random = new Random();

    public static int slots = 1;

    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    protected RunicInterceptorTile(TileEntityType<?> typeIn) {
        super(typeIn);
    }
    public RunicInterceptorTile() {
        this(TileEntityInit.RUNIC_INTERCEPTOR_TILE_TYPE.get());
    }
    @Override
    public int getSizeInventory() {
        return slots;
    }
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public ItemStack getItem() {
        return this.items.get(0);
    }
    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + CrystalDiv.MOD_ID + ".runic_interceptor");
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
        }
        return compound;
    }
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if(!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
    }
    @Override
    public void tick() {
        this.world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 0);
        this.pageTurningSpeed = this.nextPageTurningSpeed;
        this.pageAngle = this.nextPageAngle;
        PlayerEntity playerentity = this.world.getClosestPlayer((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D, 3.0D, false);
        if (playerentity != null) {
            double d0 = playerentity.getPosX() - ((double)this.pos.getX() + 0.5D);
            double d1 = playerentity.getPosZ() - ((double)this.pos.getZ() + 0.5D);
            this.field_195531_n = (float)MathHelper.atan2(d1, d0);
            this.nextPageTurningSpeed += 0.1F;
            if (this.nextPageTurningSpeed < 0.5F || random.nextInt(40) == 0) {
                float f1 = this.field_195525_h;

                do {
                    this.field_195525_h += (float)(random.nextInt(4) - random.nextInt(4));
                } while(f1 == this.field_195525_h);
            }
        } else {
            this.field_195531_n += 0.02F;
            this.nextPageTurningSpeed -= 0.1F;
        }

        while(this.nextPageAngle >= (float)Math.PI) {
            this.nextPageAngle -= ((float)Math.PI * 2F);
        }

        while(this.nextPageAngle < -(float)Math.PI) {
            this.nextPageAngle += ((float)Math.PI * 2F);
        }

        while(this.field_195531_n >= (float)Math.PI) {
            this.field_195531_n -= ((float)Math.PI * 2F);
        }

        while(this.field_195531_n < -(float)Math.PI) {
            this.field_195531_n += ((float)Math.PI * 2F);
        }

        float f2;
        for(f2 = this.field_195531_n - this.nextPageAngle; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
        }

        while(f2 < -(float)Math.PI) {
            f2 += ((float)Math.PI * 2F);
        }

        this.nextPageAngle += f2 * 0.4F;
        this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
        ++this.ticks;
        this.field_195524_g = this.field_195523_f;
        float f = (this.field_195525_h - this.field_195523_f) * 0.4F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.field_195526_i += (f - this.field_195526_i) * 0.9F;
        this.field_195523_f += this.field_195526_i;
    }
    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }
}
