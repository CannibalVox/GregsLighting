//------------------------------------------------------------------------------------------------
//
//   Greg's Mod Base - Generic Block Renderer
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

import net.minecraftforge.common.*;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class BaseBlockRenderer implements ISimpleBlockRenderingHandler {

	public int renderID;
	
	Tessellator tess;
	IBlockAccess world;
	Block block;
	int blockX, blockY, blockZ;
	int metadata;
	double u0, v0, us, vs;
	boolean textureOverridden;

	@Override
	public int getRenderId() {
		return renderID;
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks rb) {
		//System.out.printf("BaseBlockRenderer.renderInventoryBlock\n");
		this.world = null;
		this.block = block;
		blockX = 0;
		blockY = 0;
		blockZ = 0;
		setUpTextureOverride(rb);
		this.metadata = metadata;
		tess = Tessellator.instance;
		tess.setColorOpaque_F(1, 1, 1);
		tess.setBrightness(15);
		Trans3 t = new Trans3(0.5, 0.5, 0.5);
		tess.startDrawingQuads();
		renderBlock(t);
		tess.draw();
  }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, 
		Block block, int renderID, RenderBlocks rb)
	{
		//System.out.printf("BaseBlockRenderer.renderWorldBlock\n");
		this.world = world;
		this.block = block;
		blockX = x;
		blockY = y;
		blockZ = z;
		setUpTextureOverride(rb);
		metadata = world.getBlockMetadata(x, y, z);
		tess = Tessellator.instance;
		//int b = block.getMixedBrightnessForBlock(world, x, y, z);
		//tess.setBrightness(b);
		tess.setColorOpaque_F(1, 1, 1);
		Trans3 t = new Trans3(x + 0.5, y + 0.5, z + 0.5);
		return renderBlock(t);
	}
	
	abstract boolean renderBlock(Trans3 t);

	void setUpTextureOverride(RenderBlocks rb) {
		int index = rb.overrideBlockTexture;
		if (index >= 0) {
			textureOverridden = true;
			u0 = (index & 0xf) / 16.0;
			v0 = (index >> 4) / 16.0;
		}
		else
			textureOverridden = false;
	}
	
	void selectTile(int index) {
		selectTile(index >> 4, index & 0xf);
	}

	void selectTile(int row, int col) {
		selectTile(row, col, 16, 16);
	}

	void selectTile(int row, int col, int width, int height) {
		if (!textureOverridden) {
			u0 = col * (1/16.0);
			v0 = row * (1/16.0);
		}
		us = 1.0/width;
		vs = 1.0/height;
	}
	
	static double cubeFaces[][] = {
		{-0.5, -0.5,  0.5,   0,  0, -1,   1,  0,  0,   0, -1,  0}, // DOWN
		{-0.5,  0.5, -0.5,   0,  0,  1,   1,  0,  0,   0,  1,  0}, // UP
		{ 0.5,  0.5, -0.5,   0, -1,  0,  -1,  0,  0,   0,  0, -1}, // NORTH
		{-0.5,  0.5,  0.5,   0, -1,  0,   1,  0,  0,   0,  0,  1}, // SOUTH
		{-0.5,  0.5, -0.5,   0, -1,  0,   0,  0,  1,  -1,  0,  0}, // WEST
		{ 0.5,  0.5,  0.5,   0, -1,  0,   0,  0, -1,   1,  0,  0}, // EAST
	};
	
	void renderCube(Trans3 t) {
		for (int i = 0; i < 6; i++) {
			selectTileForSide(i);
			setBrightnessForSide(t, i);
			cubeFace(t, cubeFaces[i]);
		}
	}
	
	void selectTileForSide(int side) {
		selectTile(block.getBlockTextureFromSideAndMetadata(side, metadata));
	}
	
	void setBrightnessForSide(Trans3 t, int side) {
		if (world != null) {
			ForgeDirection d = ForgeDirection.getOrientation(side);
			Vector3 p = t.p(d.offsetX, d.offsetY, d.offsetZ);
			//System.out.printf("BaseBlockRenderer.setBrightnessForSide: side %d is next to (%d,%d,%d)\n",
			//	side, p.floorX(), p.floorY(), p.floorZ());
			tess.setBrightness(block.getMixedBrightnessForBlock(world, p.floorX(), p.floorY(), p.floorZ()));
		}
		else
			tess.setBrightness(15);
	}
	
	void cubeFace(Trans3 t, double[] c) {
		tess.setNormal((float) c[9], (float) c[10], (float) c[11]);
		face(t, c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7], c[8], 0, 0, 16, 16);
	}
	
	void face(Trans3 t,
		double x, double y, double z,
		double dx1, double dy1, double dz1,
		double dx2, double dy2, double dz2,
		double u, double v, double du, double dv)
	{
		vertex(t, x, y, z, u, v);
		vertex(t, x + dx1, y + dy1, z + dz1, u, v + dv);
		vertex(t, x + dx1 + dx2, y + dy1 + dy2, z + dz1 + dz2, u + du, v + dv);
		vertex(t, x + dx2, y + dy2, z + dz2, u + du, v);
	}
	
	void vertex(Trans3 t, double x, double y, double z, double u, double v) {
		if (textureOverridden) {
			u *= us;
			v *= vs;
		}
		Vector3 p = t.p(x, y, z);
		tess.addVertexWithUV(p.x, p.y, p.z, u0 + u * (1/256.0), v0 + v * (1/256.0));
	}	

}
