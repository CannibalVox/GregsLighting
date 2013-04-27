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
	}

	public void load() {
		System.out.printf("GregsLightingServer: load()\n");
	}
}