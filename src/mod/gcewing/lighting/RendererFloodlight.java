package gcewing.lighting;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererFloodlight implements ISimpleBlockRenderingHandler {

	private int mRenderId;
	
	public RendererFloodlight(int renderId)
	{
		mRenderId = renderId;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		Tessellator var4 = Tessellator.instance;
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        var4.startDrawingQuads();
        var4.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
        var4.draw();

        var4.startDrawingQuads();
        var4.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
        var4.draw();

        var4.startDrawingQuads();
        var4.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metadata));
        var4.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int var5 = world.getBlockMetadata(x, y, z) & 0x0E;

		switch (var5)
		{
		case 6:
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 2;
			renderer.uvRotateTop = 2;
			renderer.uvRotateBottom = 1;
			break;
		case 4:
			renderer.uvRotateEast = 2;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 2;
			break;
		case 8:
			renderer.uvRotateNorth = 1;
			renderer.uvRotateSouth = 2;
			renderer.uvRotateTop = 3;
			renderer.uvRotateBottom = 3;
			break;
		case 10:
			renderer.uvRotateNorth = 2;
			renderer.uvRotateSouth = 1;
			break;
		case 0:
			renderer.uvRotateSouth = 3;
			renderer.uvRotateEast = 3;
			renderer.uvRotateWest = 3;
			renderer.uvRotateNorth = 3;
			break;
		}
		
		boolean var7 = renderer.renderStandardBlock(block, x, y, z);

		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		
		return var7;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return mRenderId;
	}

}
