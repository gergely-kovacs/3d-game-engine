package util;

import org.lwjgl.glfw.GLFW;

import util.maths.Vec3f;

public class Interpolator {
	private double runTill, timePassed;
	private boolean first;
	private Vec3f current;
	
	public Interpolator() {
		this.current = new Vec3f(0.0f, 0.0f, 0.0f);
		this.first = true;
	}
	
	public Vec3f interpolate(Vec3f from, Vec3f to, float duration) {
		if(first) {
			runTill = GLFW.glfwGetTime() + duration;
			first = false;
		}
		
		timePassed = 1 - (runTill - GLFW.glfwGetTime()) / duration;
		if (timePassed >= 1.0d) this.reset();
		
		current.x = from.x + (float) (timePassed * (to.x - from.x)); // TODO: HA az uj ertek nagyobb mint a cel ertek, akkor ujertek = celertek?
		current.y = from.y + (float) (timePassed * (to.y - from.y));
		current.z = from.z + (float) (timePassed * (to.z - from.z));
		
		return current;
	}
	
	public void reset() {
		first = true;
	}
}
