//------------------------------------------------------------------------------
//
//   Greg's Lighting - ContainerFloodlightCarbide - Client
//
//------------------------------------------------------------------------------


package gcewing.lighting;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerFLCClient extends ContainerFLCBase {

	public ContainerFLCClient(InventoryPlayer pi, TEFloodlightCarbide te) {
		super(pi, te);
	}

	public void updateProgressBar(int i, int value) {
		//System.out.printf("ContainerFloodlightCarbide.updateProgressBar: %d %d\n", i, value);
		switch (i) {
			case 0: te.waterLevel = value;
			case 1: te.carbideLevel = value;
		}
	}
	
}
