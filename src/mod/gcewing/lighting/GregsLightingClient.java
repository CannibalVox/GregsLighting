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
	
	void addBlockRenderer(BaseIRenderType block, ISimpleBlockRenderingHandler renderer, int rendererId) {
		block.setRenderType(rendererId);
		RenderingRegistry.registerBlockHandler(rendererId, renderer);
	}

	public File getMinecraftDirectory() {
		return mcdir;
	}

	public void load() {
		System.out.printf("GregsLightingClient: load()\n");
		registerRenderers();
	}

	public void registerRenderers() {
		int renderID = RenderingRegistry.getNextAvailableRenderId();
		addBlockRenderer(GregsLighting.floodlight, new RendererFloodlight(renderID),renderID);
	}	
}