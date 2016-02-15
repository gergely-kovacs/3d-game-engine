package util;

import org.lwjgl.glfw.GLFW;

import util.maths.Vec3f;

public class Interpolator {
	private double runTill, timePassed;
	private boolean first, finished;
	private Vec3f current;
	
	public Interpolator() {
		current = new Vec3f();
		first = true;
		finished = false;
	}
	
	public Vec3f lerp(Vec3f from, Vec3f to, float duration) {
		if(first) {
			runTill = GLFW.glfwGetTime() + duration;
			first = false;
		}
		
		timePassed = 1 - (runTill - GLFW.glfwGetTime()) / duration;
		if (timePassed >= 1.0d) { finished = true; return current;}
		
		current.x = (float) ((1 - timePassed) * from.x + timePassed * to.x);
		current.y = (float) ((1 - timePassed) * from.y + timePassed * to.y);
		current.z = (float) ((1 - timePassed) * from.z + timePassed * to.z);
		
		return current;
	}
	
	public void reset() {
		first = true;
		finished = false;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
