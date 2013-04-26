package gcewing.lighting;

//import java.lang.Thread;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.*;

public class TEFloodlightCarbide extends TileEntity implements IInventory, ISidedInventory {

	ItemStack[] inventory = new ItemStack[2];
	final static int waterSlot = 0;
	final static int carbideSlot = 1;
	//final static int maxWaterLevel = 30 * 20;
	//final static int maxCarbideLevel = 10;
	
	final static int minutesPerCarbide = 5;
	final static int maxCarbideLevel = minutesPerCarbide * 60;
	final static int maxWaterLevel = 64 * maxCarbideLevel;
	
	int waterLevel;
	int carbideLevel;
	//boolean receivingRedstoneSignal;
	int ticks;
	
	public TEFloodlightCarbide() {
		//waterLevel = maxWaterLevel; // temp
		//carbideLevel = maxCarbideLevel; // temp
		ticks = 0;
	}
	
	public int getSizeInventory() {
		return inventory.length;
	}
	
	public ItemStack getStackInSlot(int i) {
		//if (!worldObj.isRemote) {
		//	System.out.printf("\nTEFloodlightCarbide.getStackInSlot: %d on %s\n", i, worldObj);
		//	Thread.dumpStack();
		//}
		return inventory[i];
	}
	
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack stack) {
		//System.out.printf("TEFloodlightCarbide.setInventorySlotContents: %d to %s on %s\n",
		//	i, stack, worldObj);
		inventory[i] = stack;
		onInventoryChanged();
		refillWater();
		//refillCarbide();
		updateBlock();
	}
	
	public String getInvName() {
		return "container.carbide_floodlight";
	}
	
	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg.
	 * Returns the new stack.
	 */
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = this.inventory[slot];
		if (stack != null) {
			if (stack.stackSize <= amount)
				this.inventory[slot] = null;
			else
				stack = stack.splitStack(amount);
			onInventoryChanged();
		}
		return stack;
	}

	/**
	 * Get the start of the side inventory.
	 * @param side The global side to get the start of range.
	 *		0: -Y (bottom side)
	 *		1: +Y (top side)
	 *		2: -Z
	 *		3: +Z
	 *		4: -X
	 *		5: +x
	 */
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.UP)
			return waterSlot;
		else
			return carbideSlot;
	}

	/**
	 * Get the size of the side inventory.
	 * @param side The global side.
	 */
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	public void readFromNBT(NBTTagCompound tc) {
		super.readFromNBT(tc);
		NBTTagList tlist = tc.getTagList("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tlist.tagCount(); i++) {
			NBTTagCompound titem = (NBTTagCompound)tlist.tagAt(i);
			byte s = titem.getByte("Slot");
			if (s >= 0 && s < this.inventory.length)
				this.inventory[s] = ItemStack.loadItemStackFromNBT(titem);
		}
		waterLevel = tc.getInteger("Water");
		carbideLevel = tc.getInteger("Carbide");
		//receivingRedstoneSignal = tc.getBoolean("Redstoned");
		//System.out.printf("TEFloodlightCarbide.readFromNBT: water %d carbide %d\n",
		//	waterLevel, carbideLevel);
	}

	public void writeToNBT(NBTTagCompound tc) {
		super.writeToNBT(tc);
		NBTTagList tlist = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++) {
			if (this.inventory[i] != null) {
				NBTTagCompound titem = new NBTTagCompound();
				titem.setByte("Slot", (byte)i);
				this.inventory[i].writeToNBT(titem);
				tlist.appendTag(titem);
			}
		}
		tc.setTag("Items", tlist);
		tc.setInteger("Water", waterLevel);
		tc.setInteger("Carbide", carbideLevel);
		//tc.setBoolean("Redstoned", receivingRedstoneSignal);
		//System.out.printf("TEFloodlightCarbide.writeToNBT: water %d carbide %d\n",
		//	waterLevel, carbideLevel);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this)
			return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
		else
			return false;
	}

	public void openChest() {}

	public void closeChest() {}
	
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (ticks == 0) {
				ticks = 20;
				//System.out.printf("TEFloodlightCarbide.updateEntity: water %d carbide %d\n",
				//	waterLevel, carbideLevel);
				if (isActive()) {
					waterLevel -= 1;
					refillWater();
					carbideLevel -= 1;
					refillCarbide();
					//onInventoryChanged();
					if (!isActive())
						updateBlock();
				}
			}
			--ticks;
		}
	}
	
	void updateBlock() {
		GregsLighting.floodlightCarbide.update(worldObj, xCoord, yCoord, zCoord);
	}
	
	public boolean isActive() {
		return waterLevel > 0 && carbideLevel > 0 && receivingRedstoneSignal();
	}
	
	boolean receivingRedstoneSignal() {
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
	
	void refillWater() {
		// If water tank is empty, try to refill it from the water bucket slot
		if (waterLevel == 0) {
			ItemStack stack = inventory[waterSlot];
			if (Water.isWaterItem(stack)) {
				waterLevel = maxWaterLevel;
				setInventorySlotContents(waterSlot, Water.emptyContainerFor(stack));
				//onInventoryChanged();
			}
		}
	}
	
	void refillCarbide() {
		// If reaction chamber is empty, try to refill it from the carbide slot
		if (carbideLevel == 0 && waterLevel > 0 && receivingRedstoneSignal()) {
			ItemStack stack = inventory[carbideSlot];
			if (stack != null && stack.getItem() == GregsLighting.calciumCarbide) {
				decrStackSize(carbideSlot, 1);
				carbideLevel = maxCarbideLevel;
			}
		}
	}
	
}
