//------------------------------------------------------------------------------------------------
//
//   Greg's Mod Base - Generic Block with Tile Entity
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.registry.*;

public class BaseContainerBlock<TE extends TileEntity>
	extends BlockContainer implements BaseIRenderType
{

	public int renderID = 0;
	Class<? extends TileEntity> tileEntityClass = null;

	public BaseContainerBlock(int id, Material material, Class<TE> teClass) {
		super(id, material);
		//setTextureFile(BaseMod.textureFile);
		tileEntityClass = teClass;
		GameRegistry.registerTileEntity(teClass, teClass.getName());
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

	public TE getTileEntity(IBlockAccess world, int x, int y, int z) {
		return (TE)world.getBlockTileEntity(x, y, z);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		try {
			return tileEntityClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
	
}
