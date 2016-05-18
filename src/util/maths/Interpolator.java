package util.maths;

import org.lwjgl.glfw.GLFW;

public class Interpolator {
	private double runTill, timePassed;
	private boolean init, finished;
	private Vec3f current;
	
	public Interpolator() {
		current = new Vec3f();
		init = true;
		finished = false;
	}
	
	public Vec3f lerp(Vec3f from, Vec3f to, float duration) {
		if(init) {
			runTill = GLFW.glfwGetTime() + duration;
			init = false;
		}
		
		timePassed = 1 - (runTill - GLFW.glfwGetTime()) / duration;
		if (timePassed >= 1.0d) { finished = true; return to;}
		
		current.x = (float) ((1 - timePassed) * from.x + timePassed * to.x);
		current.y = (float) ((1 - timePassed) * from.y + timePassed * to.y);
		current.z = (float) ((1 - timePassed) * from.z + timePassed * to.z);
		
		return current;
	}
	
	public void reset() {
		init = true;
		finished = false;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
