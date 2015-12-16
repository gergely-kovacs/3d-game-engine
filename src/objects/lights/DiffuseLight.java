package objects.lights;

import util.maths.Vec3f;

public class DiffuseLight extends Light{
	
	public DiffuseLight(Vec3f position, Vec3f colour) {
		super(position, colour);
	}
	
	public void rise(float stepSize) {
		this.position.x += stepSize;
		this.position.y += stepSize;
	}
	
	public void set(float stepSize) {
		this.position.x += stepSize;
		this.position.y -= stepSize;
	}
	
	public void dawn() {
		if (this.colour.x < 0.85f)
			this.colour.x += 0.05;
		if (this.colour.y < 0.8f)
			this.colour.y += 0.05;
		if (this.colour.z < 0.75f)
			this.colour.z += 0.05;
	}
	
	public void dusk() {
		if (this.colour.x >= 0.0f)
			this.colour.x -= 0.05;
		if (this.colour.y >= 0.0f)
			this.colour.y -= 0.05;
		if (this.colour.z >= 0.0f)
			this.colour.z -= 0.05;
	}
	
}
