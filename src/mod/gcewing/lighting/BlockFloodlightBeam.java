//------------------------------------------------------
//
//   Greg's Lighting - Floodlight Beam Block
//
//------------------------------------------------------

package gcewing.lighting;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockFloodlightBeam extends BaseContainerBlock<TEFloodlightBeam> {

	public BlockFloodlightBeam(int id) {
		super(id, Material.vine, TEFloodlightBeam.class);
		setLightValue(1.0F);
		setLightOpacity(0);
		setHardness(-1);
		setResistance(6000000);
		if (Floodlight.debugBeamBlocks)
			setBlockBounds(3/8.0F, 3/8.0F, 3/8.0F, 5/8.0F, 5/8.0F, 5/8.0F);
		else
			setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
	}
	
	public String getTextureFile() {
		return "/gcewing/lighting/resources/textures.png";
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TEFloodlightBeam();
	}

	public boolean isOpaqueCube() {
		return false;
	}
    
	public boolean isAirBlock(World world, int x, int y, int z)  {
		return true;
	}
	
	@Override
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isLeaves(World world, int x, int y, int z) {
		// This is a bit screwy, but it's needed so that trees are not prevented from growing
		// near a floodlight beam.
		return true;
	}

	public boolean renderAsNormalBlock() {
		//return false;
		return Floodlight.debugBeamBlocks;
	}
	
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	public int getRenderType() {
		if (Floodlight.debugBeamBlocks)
			return super.getRenderType();
		else
			return -1;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		Floodlight.updateBeams(world, x, y, z);
	}
	
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		//System.out.printf("BlockFloodlightBeam.getBlockTexture for side %d at (%d,%d,%d)\n",
		//	side, x, y, z);
		TEFloodlightBeam te = (TEFloodlightBeam)world.getBlockTileEntity(x, y, z);
		//System.out.printf("BlockFloodlightBeam.getBlockTexture: intensity = %d\n", te.intensity[side]);
		if (te.intensity[side] > 0)
			return 0x31;
		else
			return 0x30;
	}

}
