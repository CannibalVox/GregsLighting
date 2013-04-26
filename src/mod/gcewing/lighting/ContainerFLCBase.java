package gcewing.lighting;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class ContainerFLCBase extends Container {

	TEFloodlightCarbide te;
	
	public ContainerFLCBase(InventoryPlayer pi, TEFloodlightCarbide te) {
		this.te = te;
		addSlotToContainer(new Slot(te, 0, 44, 8));
		addSlotToContainer(new Slot(te, 1, 116, 60));
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				this.addSlotToContainer(new Slot(pi, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; ++i)
			this.addSlotToContainer(new Slot(pi, i, 8 + i * 18, 142));
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNo) {
		// TODO
		return null;
	}

}
