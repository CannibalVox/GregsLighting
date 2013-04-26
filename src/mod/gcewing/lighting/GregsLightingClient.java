//------------------------------------------------------
//
//   Greg's Lighting Mod - Client Proxy
//
//------------------------------------------------------

package gcewing.lighting;

import java.io.File;
//import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;
import net.minecraft.src.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.common.*;
import cpw.mods.fml.client.registry.*;

import gcewing.lighting.*;

public class GregsLightingClient extends GregsLightingBase {

	public static Minecraft minecraft;
	public static File mcdir;
		
	public GregsLightingClient() {
		System.out.printf("GregsLightingClient()\n");
		minecraft = ModLoader.getMinecraftInstance();
		mcdir = Minecraft.getMinecraftDir();
	}
	
	void addBlockRenderer(BaseIRenderType block, BaseBlockRenderer renderer) {
		int renderID = RenderingRegistry.getNextAvailableRenderId();
		block.setRenderType(renderID);
		renderer.renderID = renderID;
		RenderingRegistry.registerBlockHandler(renderID, renderer);
	}

	public File getMinecraftDirectory() {
		return mcdir;
	}

	public void load() {
		System.out.printf("GregsLightingClient: load()\n");
		loadTextures();
		registerRenderers();
	}
	
	void loadTextures() {
		System.out.printf("GregsLightingClient: loadTextures()\n");
		MinecraftForgeClient.preloadTexture(GregsLighting.textureFile);
	}

	public void registerRenderers() {
		addBlockRenderer(GregsLighting.floodlight, new RendererFloodlight());
	}

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

//	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
//		System.out.printf("GregsLightingClient.getServerGuiElement: %d\n", id);
//		return null;
//	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		//System.out.printf("GregsLightingClient.getClientGuiElement: %d\n", id);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null) {
			//System.out.printf("GregsLightingClient.getGuiElement: have te\n");
			switch (id) {
				case GregsLighting.guiFloodlightCarbide:
					return new GuiFloodlightCarbide(player.inventory, (TEFloodlightCarbide)te);
			}
		}
		return null;
	}

//	public boolean shiftKeyDown() {
//		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
//	}
	
}
