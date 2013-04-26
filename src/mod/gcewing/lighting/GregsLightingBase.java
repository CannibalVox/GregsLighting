//------------------------------------------------------
//
//   Greg's Lighting Mod - Proxy Base
//
//------------------------------------------------------

package gcewing.lighting;

import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

import gcewing.lighting.*;

import java.io.File;

public abstract class GregsLightingBase implements IGuiHandler {

	public abstract File getMinecraftDirectory();
	public abstract void load();
	
	/**
	 * Returns a Container to be displayed to the user. 
	 * On the client side, this needs to return a instance of GuiScreen
	 * On the server side, this needs to return a instance of Container
	 *
	 * @param ID The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */

	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		//System.out.printf("GregsLightingBase.getServerGuiElement %d\n", id);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null) {
			//System.out.printf("GregsLightingBase.getGuiElement: have TE\n");
			switch (id) {
				case GregsLighting.guiFloodlightCarbide:
					//System.out.printf("GregsLightingServer.getGuiElement: creating ContainerFloodlightCarbide\n");
					return new ContainerFLCServer(player.inventory, (TEFloodlightCarbide)te);
			}
		}
		return null;
	}

}
