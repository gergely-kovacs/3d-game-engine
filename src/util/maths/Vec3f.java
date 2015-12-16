package util.maths;

public class Vec3f {
	public float x, y, z;
	
	public Vec3f() {
	}

	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3f(Vec3f other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
	
	public Vec3f normalize() {
		float length = (float) Math.sqrt(x * x +  y * y + z * z);
		x = x / length;
		y = y / length;
		z = z / length;
		return this;
	}
	
	public Vec3f add(Vec3f other) {
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Vec3f subtract(Vec3f other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public float dot(Vec3f other) {
		float dotProduct;
		dotProduct = x * other.x + y * other.y + z * other.z;
		return dotProduct;
	}
	
	public Vec3f cross(Vec3f other) {
		Vec3f crossProduct = new Vec3f();
		crossProduct.x = y * other.z - z * other.y;
		crossProduct.y = z * other.x - x * other.z;
		crossProduct.z = x * other.y - y * other.x;
		return crossProduct;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vec3f other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
}