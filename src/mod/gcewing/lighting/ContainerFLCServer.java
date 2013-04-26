//------------------------------------------------------------------------------
//
//   Greg's Lighting - ContainerFloodlightCarbide - Server
//
//------------------------------------------------------------------------------


package gcewing.lighting;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerFLCServer extends ContainerFLCBase {

	public ContainerFLCServer(InventoryPlayer pi, TEFloodlightCarbide te) {
		super(pi, te);
	}

//	public void onCraftGuiOpened(ICrafting crafter) {
//		super.onCraftGuiOpened(crafter);
//		sendStateTo(crafter);
//	}
	
	public void updateCraftingResults() {
		super.updateCraftingResults();
		for (int i = 0; i < crafters.size(); i++) {
			ICrafting crafter = (ICrafting)crafters.get(i);
			sendStateTo(crafter);
		}
	}
	
	void sendStateTo(ICrafting crafter) {
		crafter.sendProgressBarUpdate(this, 0, te.waterLevel);
		crafter.sendProgressBarUpdate(this, 1, te.carbideLevel);
	}

}
