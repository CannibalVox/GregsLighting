//------------------------------------------------------------------------------------------------
//
//   Greg's Lighting - Floodlight block renderer
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

public class RendererFloodlight extends BaseBlockRenderer {

	@Override
	public boolean renderBlock(Trans3 t) {
		//System.out.printf("RendererFloodlight at (%d,%d,%d): orientation = %d\n",
		//	blockX, blockY, blockZ, metadata >> 1);
		renderCube(t.side(metadata >> 1));
		return false;
	}
	
}
