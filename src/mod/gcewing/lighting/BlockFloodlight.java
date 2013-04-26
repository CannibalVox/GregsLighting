package gcewing.lighting;

import java.util.ArrayList;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

public class BlockFloodlight extends BlockContainer implements BaseIRenderType {

	static int renderType;

	public BlockFloodlight(int id) {
		super(id, 0, Material.rock);
	}
	
	public BlockFloodlight(int id, int tex, Material mat) {
		super(id, tex, mat);
//		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}
	
	@Override
	public void setRenderType(int id) {
		renderType = id;
	}
	
	@Override
	public String getTextureFile() {
		return "/gcewing/lighting/resources/textures.png";
	}
	
	@Override
	public int func_85104_a(World world, int x, int y, int z, int side, float hx, float hy, float hz, int initData) {
		return side << 1;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int data) {
		int base = blockIndexInTexture;
		if ((data & 1) != 0)
			base += 3;
		switch (side) {
			case 0: return base; // bottom
			case 1: return base + 2; // top
			default: return base + 1; // sides
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		update(world, x, y, z);
		updateBeam(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int metadata) {
		ForgeDirection dir = direction(metadata);
		Floodlight.propagateBeam(world, x, y, z, dir, 0, 1);
		super.breakBlock(world, x, y, z, id, metadata);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		update(world, x, y, z);
		updateBeam(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	public void update(World world, int x, int y, int z) {
		if (!world.isRemote) {
			boolean active = isActive(world, x, y, z);
			setIlluminated(world, x, y, z, active);
		}
	}

	public void updateBeam(World world, int x, int y, int z) {
		if (!world.isRemote) {
			ForgeDirection dir = getDirection(world, x, y, z);
			Floodlight.updateBeamInDirection(world,
				x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
		}
	}
	
	public void setIlluminated(World world, int x, int y, int z, boolean state) {
		//System.out.printf("BlockFloodlight.setIlluminated: %s\n", state);
		int oldData = world.getBlockMetadata(x, y, z);
		int newData = (oldData & 0xe) | (state ? 1 : 0);
		if (oldData != newData) {
			world.setBlockMetadata(x, y, z, newData);
			world.markBlockForUpdate(x, y, z);
		}
	}

	public boolean isActive(World world, int x, int y, int z) {
		return world.isBlockIndirectlyGettingPowered(x, y, z);
	}
	
	public boolean isActiveInDirection(World world, int x, int y, int z, ForgeDirection dir) {
		return getDirection(world, x, y, z) == dir && isActive(world, x, y, z);
	}
	
	public ForgeDirection getDirection(World world, int x, int y, int z) {
		int data = world.getBlockMetadata(x, y, z);
		return direction(data);
	}
	
	public ForgeDirection direction(int metadata) {
		return ForgeDirection.getOrientation(metadata >> 1);
	}
	
}
