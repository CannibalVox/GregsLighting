//------------------------------------------------------
//
//   Greg's Lighting Mod - Server Proxy
//
//------------------------------------------------------

package gcewing.lighting;

import java.io.File;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;

import gcewing.lighting.*;

public class GregsLightingServer extends GregsLightingBase {

	public GregsLightingServer() {
		System.out.printf("GregsLightingServer()\n");
		GregsLighting.isServer = true;
	}
	
	public File getMinecraftDirectory() {
		return new File(".");
	}

	public void load() {
		System.out.printf("GregsLightingServer: load()\n");
	}
	
//	public void onPacket250Received(EntityPlayer player, Packet250CustomPayload pkt) {
//	}

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

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.printf("GregsLightingServer.getClientGuiElement %d\n", id);
		return null;
	}

}
