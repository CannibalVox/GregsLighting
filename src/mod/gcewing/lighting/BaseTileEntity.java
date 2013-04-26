//------------------------------------------------------------------------------------------------
//
//   Greg's Mod Base - Generic Tile Entity
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.tileentity.*;
import net.minecraft.src.*;
import net.minecraft.src.*;

public class BaseTileEntity extends TileEntity {

	@Override
	public Packet getDescriptionPacket() {
		//System.out.printf("BaseTileEntity.getDescriptionPacket for %s\n", this);
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		//System.out.printf("BaseTileEntity.onDataPacket for %s\n", this);
		readFromNBT(pkt.customParam1);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
}
