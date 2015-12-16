package objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.maths.Vec3f;
import world.World;

public class TexturedModel{
	private Vec3f position, direction, scale;
	private float shineDamper, reflectivity;
	private int texId, vaoId, vertexCount, shineDamperUniLoc, reflectivityUniLoc;

	public TexturedModel(Model model, Texture texture,
			Vec3f position, Vec3f direction, Vec3f scale,
			float shineDamper, float reflectivity) {
		texId = texture.getId();
		
		vaoId = model.getVaoId();
		vertexCount = model.getVertexCount();
		
		this.position = position;
		this.direction = direction;
		
		shineDamperUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "shineDamper");
		reflectivityUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "reflectivity");
	}
	
	public void render() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void specifyUniforms() {
		GL20.glUniform1f(shineDamperUniLoc, shineDamper);
        GL20.glUniform1f(reflectivityUniLoc, reflectivity);
	}
	
	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public Vec3f getAngle() {
		return direction;
	}

	public void setAngle(Vec3f direction) {
		this.direction = direction;
	}
	
	public void setAngle(float x, float y, float z) {
		direction.x = x;
		direction.y = y;
		direction.z = z;
	}

	public Vec3f getScale() {
		return scale;
	}

	public void setScale(Vec3f scale) {
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z) {
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

}
