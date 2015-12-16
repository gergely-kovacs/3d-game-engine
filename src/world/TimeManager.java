package world;

import org.lwjgl.glfw.GLFW;

import util.Interpolator;
import util.maths.Vec3f;

public class TimeManager {
	private double dayTracker;
	private int dayLength;
	private Interpolator positionInterp, colorInterp;
	
	public TimeManager(int dayLength) {
		this.dayLength = dayLength;
		
		dayTracker = GLFW.glfwGetTime();
		
		positionInterp = new Interpolator();
		colorInterp = new Interpolator();
	}
	
	public void passTime() {
		if (GLFW.glfwGetTime() - dayTracker >= dayLength / 3 &&
			GLFW.glfwGetTime() - dayTracker < dayLength * 2 / 3) {
			
			World.sun.setPosition(positionInterp.interpolate(new Vec3f(-100.0f, 0.0f, 25.0f), new Vec3f(0.0f, 100.0f, 25.0f), dayLength / 3));
			
			if (GLFW.glfwGetTime() - dayTracker >= dayLength / 3 &&
				GLFW.glfwGetTime() - dayTracker < dayLength * 3 / 8) {
				World.sun.setColour(colorInterp.interpolate(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.85f, 0.8f, 0.75f), dayLength / 24));
			}
		} else if (GLFW.glfwGetTime() - dayTracker >= dayLength * 2 / 3 &&
			GLFW.glfwGetTime() - dayTracker < dayLength) {
			
			World.sun.setPosition(positionInterp.interpolate(new Vec3f(0.0f, 100.0f, 25.0f), new Vec3f(100.0f, 0.0f, 25.0f), dayLength / 3));
			
			if (GLFW.glfwGetTime() - dayTracker >= dayLength * 23 / 24 &&
				GLFW.glfwGetTime() - dayTracker < dayLength) {
				World.sun.setColour(colorInterp.interpolate(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.85f, 0.8f, 0.75f), dayLength / 24));
			}
		} else if (GLFW.glfwGetTime() - dayTracker >= dayLength) {
			World.sun.setPosition(new Vec3f(-100.0f, 0.0f, 25.0f));
			World.sun.setColour(new Vec3f(0.0f, 0.0f, 0.0f));
			dayTracker = GLFW.glfwGetTime();
		}
	}
}
