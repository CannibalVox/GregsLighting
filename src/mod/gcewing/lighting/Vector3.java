//------------------------------------------------------------------------------------------------
//
//   Greg's Mod Base - 3D Vector
//
//------------------------------------------------------------------------------------------------

package gcewing.lighting;

import net.minecraft.util.*;

public class Vector3 {

	static Vector3 zero = new Vector3(0, 0, 0);

	static Vector3 unitX = new Vector3(1, 0, 0);
	static Vector3 unitY = new Vector3(0, 1, 0);
	static Vector3 unitZ = new Vector3(0, 0, 1);
	
	static Vector3 unitNX = new Vector3(-1, 0, 0);
	static Vector3 unitNY = new Vector3(0, -1, 0);
	static Vector3 unitNZ = new Vector3(0, 0, -1);
	
	static Vector3 unitPYNZ = new Vector3(0, 0.707, -0.707);
	static Vector3 unitPXPY = new Vector3(0.707, 0.707, 0);
	static Vector3 unitPYPZ = new Vector3(0, 0.707, 0.707);
	static Vector3 unitNXPY = new Vector3(-0.707, 0.707, 0);

	double x, y, z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vec3 v) {
		this(v.xCoord, v.yCoord, v.zCoord);
	}
	
	public Vec3 toVec3() {
		return Vec3.createVectorHelper(x, y, z);
	}
	
	public String toString() {
		return "Vector3(" + x + "," + y + "," + z + ")";
	}
	
	public Vector3 add(double x, double y, double z) {
		return new Vector3(this.x + x, this.y + y, this.z + z);
	}
	
	public Vector3 add(Vector3 v) {
		return add(v.x, v.y, v.z);
	}
	
	public Vector3 sub(double x, double y, double z) {
		return new Vector3(this.x - x, this.y - y, this.z - z);
	}
	
	public Vector3 sub(Vector3 v) {
		return sub(v.x, v.y, v.z);
	}
	
	public Vector3 mul(double c) {
		return new Vector3(c * x, c * y, c * z);
	}
	
	public double dot(Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
	}
	
	public int floorX() {
		return (int)Math.floor(x);
	}

	public int floorY() {
		return (int)Math.floor(y);
	}

	public int floorZ() {
		return (int)Math.floor(z);
	}

}
