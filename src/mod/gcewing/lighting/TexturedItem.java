package gcewing.lighting;

import net.minecraft.item.*;

public class TexturedItem extends Item {

	public TexturedItem(int id) {
		super(id);
	}

	public String getTextureFile() {
		return GregsLighting.textureFile;
	}

}
