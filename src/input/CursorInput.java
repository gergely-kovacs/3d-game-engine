package input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import util.maths.Vec2f;
import world.World;

public class CursorInput extends GLFWCursorPosCallback {
	
	private DoubleBuffer xBuffer, yBuffer;
	private final float SENSITIVITY = 0.2f;
	
	public CursorInput() {
		xBuffer = BufferUtils.createDoubleBuffer(1);
		yBuffer = BufferUtils.createDoubleBuffer(1);
	}
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);
		
		if (yBuffer.get(0) < 90 / SENSITIVITY && yBuffer.get(0) > -90 / SENSITIVITY) {
			World.camera.setPitch((float) ypos * SENSITIVITY);
		} else if (yBuffer.get(0) < -90 / SENSITIVITY) {
			GLFW.glfwSetCursorPos(window, xpos, -90.0d / SENSITIVITY);
		} else if (yBuffer.get(0) > 90 / SENSITIVITY) {
			GLFW.glfwSetCursorPos(window, xpos, 90.0d / SENSITIVITY);
		}
		
		if (xBuffer.get(0) < 360 / SENSITIVITY && xBuffer.get(0) > -360 / SENSITIVITY) {
			World.camera.setYaw((float) xpos * SENSITIVITY);
		} else if (xBuffer.get(0) < -360 / SENSITIVITY) {
			GLFW.glfwSetCursorPos(window, -xpos % 360.0d, ypos);
		} else if (xBuffer.get(0) > 360 / SENSITIVITY) {
			GLFW.glfwSetCursorPos(window, xpos % 360.0d, ypos);
		}
	}
	
	public Vec2f getCursorPosition() {
		return new Vec2f((float) xBuffer.get(0), (float) yBuffer.get(0));
	}

}
