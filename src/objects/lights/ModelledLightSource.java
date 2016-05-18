package objects.lights;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import objects.Model;
import objects.Texture;
import util.maths.Mat4f;
import util.maths.Vec3f;

public class ModelledLightSource extends LightSource{
	public static final Vec3f
			SUN_INITIAL_POSITION = new Vec3f(-490.0f, -50.0f, -50.0f),
			SUN_NOON_POSITION = new Vec3f(0.0f, 490.0f, -50.0f),
			SUN_FINAL_POSITION = new Vec3f(490.0f, -50.0f, -50.0f),
			
			SUN_INITIAL_COLOR = new Vec3f(0.0f, 0.0f, 0.0f),
			SUN_DAWN_COLOR = new Vec3f(0.55f, 0.5f, 0.35f),
			SUN_NOON_COLOR = new Vec3f(0.85f, 0.8f, 0.65f),
			SUN_DUSK_COLOR = new Vec3f(0.55f, 0.5f, 0.35f),
			SUN_FINAL_COLOR = new Vec3f(0.0f, 0.0f, 0.0f);
	
	private Vec3f scale;
	private FloatBuffer matBuff;
	private Mat4f mMat;
	private int vaoId, texId, vertexCount, mMatUniLoc;
		
	public ModelledLightSource(Model model, Texture texture, Vec3f position, Vec3f colour) {
		super(position, colour);
		
		texId = texture.getId();
		
		vaoId = model.getVaoId();
		vertexCount = model.getVertexCount();
		
		matBuff = BufferUtils.createFloatBuffer(16);
		mMat = new Mat4f();
		
		scale = new Vec3f(8.0f, 8.0f, 8.0f);
		
		transform();
	}

	public void transform() {
		mMat.loadIdentity();
		mMat.translate(position.x, position.y, position.z);
		mMat.scale(scale.x, scale.y, scale.z);
	}
	
	public void render() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void specifyUniforms() {
		super.specifyUniforms();
		mMat.store(matBuff);
        GL20.glUniformMatrix4fv(mMatUniLoc, false, matBuff);
	}
	
	public void getUniforms(int shaderProgramId) {
		super.getUniforms(shaderProgramId);
		mMatUniLoc = GL20.glGetUniformLocation(shaderProgramId, "model");
	}
}
