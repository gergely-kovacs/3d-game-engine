package objects.lights;

import org.lwjgl.opengl.GL20;

import util.maths.Vec3f;
import world.World;

public class Light {
	protected Vec3f position;
	protected Vec3f colour;
	
	protected int posUniLoc, colUniLoc;
	
	public Light(Vec3f position, Vec3f colour) {
		this.position = position;
		this.colour = colour;
		
		posUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "lightPosition");
		colUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "lightColour");
	}
	
	public void specifyUniforms() {
		GL20.glUniform3f(posUniLoc, position.x, position.y, position.z);
        GL20.glUniform3f(colUniLoc, colour.x, colour.y, colour.z);
	}
	
	public void getUniforms(int programID) {
		
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
