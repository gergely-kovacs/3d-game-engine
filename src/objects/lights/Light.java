package objects.lights;

import org.lwjgl.opengl.GL20;

import util.maths.Vec3f;

public class Light {
	protected Vec3f position;
	protected Vec3f colour;
	
	protected int posUniLoc, colUniLoc;
	
	public Light(Vec3f position, Vec3f colour) {
		this.position = position;
		this.colour = colour;
	}
	
	public void specifyUniforms() {
		GL20.glUniform3f(posUniLoc, position.x, position.y, -position.z); // reverse the z position because in OpenGL -Z is forward
        GL20.glUniform3f(colUniLoc, colour.x, colour.y, colour.z);
	}
	
	public void getUniforms(int shaderProgramId) {
		posUniLoc = GL20.glGetUniformLocation(shaderProgramId, "lightPosition");
		colUniLoc = GL20.glGetUniformLocation(shaderProgramId, "lightColour");
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Vec3f getColour() {
		return colour;
	}

	public void setColour(Vec3f colour) {
		this.colour = colour;
	}
	
}
