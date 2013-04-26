//------------------------------------------------------------------------------------------------
//
//   Greg's Mod Base - Generic Block
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class BaseBlock extends Block implements BaseIRenderType {

	public int renderID = 0;
	
	public BaseBlock(int id, Material material) {
		super(id, material);
		//setTextureFile(BaseMod.textureFile);
	}
	
	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public void setRenderType(int id) {
		renderID = id;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return renderID == 0;
	}
	
}
