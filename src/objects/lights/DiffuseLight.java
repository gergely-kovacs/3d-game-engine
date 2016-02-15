package objects.lights;

import util.maths.Vec3f;

public class DiffuseLight extends Light{
	public static final Vec3f
	SUN_INITIAL_POSITION = new Vec3f(-0.9f, 0.35f, 0.5f),
	SUN_NOON_POSITION = new Vec3f(0.0f, 0.9f, 0.5f),
	SUN_FINAL_POSITION = new Vec3f(0.9f, 0.35f, 0.5f),
	
	SUN_INITIAL_COLOR = new Vec3f(0.0f, 0.0f, 0.0f),
	SUN_DAWN_COLOR = new Vec3f(0.75f, 0.7f, 0.55f),
	SUN_NOON_COLOR = new Vec3f(0.85f, 0.8f, 0.75f),
	SUN_DUSK_COLOR = new Vec3f(0.75f, 0.65f, 0.6f),
	SUN_FINAL_COLOR = new Vec3f(0.0f, 0.0f, 0.0f);
		
	public DiffuseLight(Vec3f position, Vec3f colour) {
		super(position, colour);
	}
}
