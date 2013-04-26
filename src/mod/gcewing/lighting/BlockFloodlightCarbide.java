package gcewing.lighting;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockFloodlightCarbide extends BlockFloodlight {

	public BlockFloodlightCarbide(int id) {
		super(id, 16, Material.rock);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
		int par6, float par7, float par8, float par9)
	{
		//System.out.printf("BlockFloodlightCarbide.onBlockActivated: server = %s, remote world = %s\n",
		//	GregsLighting.isServer, world.isRemote);
		if (!player.isSneaking()) {
			if (!world.isRemote) {
				System.out.printf("BlockFloodlightCarbide.blockActivated: opening gui\n");
				GregsLighting.openGuiFloodlightCarbide(world, x, y, z, player);
			}
			return true;
		}
		else
			return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		//System.out.printf("BlockFloodlightCarbide.createNewTileEntity\n");
		return new TEFloodlightCarbide();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int metadata) {
		Utils.dumpInventoryIntoWorld(world, x, y, z);
		super.breakBlock(world, x, y, z, id, metadata);
	}
	
	@Override
	public boolean isActive(World world, int x, int y, int z) {
		TEFloodlightCarbide te = (TEFloodlightCarbide)world.getBlockTileEntity(x, y, z);
		return te != null && te.isActive();
	}
	
	@Override
	public void update(World world, int x, int y, int z) {
		if (!world.isRemote) {
			TEFloodlightCarbide te = (TEFloodlightCarbide)world.getBlockTileEntity(x, y, z);
			if (te != null)
				te.refillCarbide();
		}
		super.update(world, x, y, z);
	}

}
