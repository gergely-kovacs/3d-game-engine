package util.maths;

import world.World;

public class BoundingSphere {
	
	public float x, y, z, radius;
	
	public BoundingSphere(Vec3f position, float scale) {
		x = position.x;
		y = position.y;
		z = position.z;
		radius = scale;
	}
	
	public boolean isPointInside(Vec3f p) {
		return ((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y) + (p.z - z) * (p.z - z)) < radius * radius;
	}
	
	public void transform(Vec3f position, float size) {
		translate(position);
		scale(size);
	}
	
	public void translate(Vec3f translation) {
		x = translation.x;
		y = translation.y + World.camera.HEIGHT;
		z = translation.z;
	}
	
	public void scale(float scale) {
		radius = scale;
	}
}
