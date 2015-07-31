package utils.maths;

public class Vec2f {
	public float x, y;
	
	public Vec2f() {
	}

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f(Vec2f other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vec2f normalize() {
		float length = (float) Math.sqrt(x * x +  y * y);
		x = x / length;
		y = y / length;
		return this;
	}
	
	public Vec2f add(Vec3f other) {
		x += other.x;
		y += other.y;
		return this;
	}
	
	public Vec2f subtract(Vec3f other) {
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	public float dot(Vec3f other) {
		float dotProduct;
		dotProduct = x * other.x + y * other.y;
		return dotProduct;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vec2f other) {
		this.x = other.x;
		this.y = other.y;
	}
}