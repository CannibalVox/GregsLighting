package gcewing.lighting;

import java.util.ArrayList;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.*;
import net.minecraft.util.Icon;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

public class BlockFloodlight extends BlockContainer implements BaseIRenderType {

	static int renderType;
	
	private String mIconBaseName;
	
	private Icon[] mFrontIcons = new Icon[2];
	private Icon[] mSideIcons  = new Icon[2];
	private Icon[] mRearIcons = new Icon[2];

	public BlockFloodlight(int id) {
		this(id, "spotlight", Material.rock);
	}
	
	public BlockFloodlight(int id, String iconBase, Material mat) {
		super(id, mat);
		mIconBaseName = iconBase;
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
	public Icon getIcon(int side, int metadata) {
		int lightActive = 0;
		
		if ((metadata) != 0)
			lightActive = 1;
		
		switch (side) {
			case 0: return mFrontIcons[lightActive]; // bottom
			case 1: return mRearIcons[lightActive]; // top
			default: return mSideIcons[lightActive]; // sides
		}
	}
	
    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
	@Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        return par5 << 1;
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
			world.setBlockMetadataWithNotify(x, y, z, newData, 3);
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
	
	@Override
	public void registerIcons(IconRegister icon)
	{
		mFrontIcons[0] = icon.registerIcon("gregslighting/spotlightOff.front");
		mFrontIcons[1] = icon.registerIcon("gregslighting/spotlightOn.front");
		mSideIcons[0] = icon.registerIcon("gregslighting/spotlightOff.side");
		mSideIcons[1] = icon.registerIcon("gregslighting/spotlightOn.side");
		mRearIcons[0] = icon.registerIcon("gregslighting/spotlightOff.rear");
		mRearIcons[1] = icon.registerIcon("gregslighting/spotlightOn.rear");
	}
}
