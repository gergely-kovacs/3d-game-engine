package display.rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import util.maths.Mat4f;
import util.maths.Vec3f;
import world.World;

public class Renderer {
	private int shaderProgramId;
	
	private FloatBuffer matBuff;
	private Mat4f vMat;
	private int vMatUniLoc;
	
	public Renderer(ShaderProgram sp) {
		shaderProgramId = sp.getProgramId();
		
		matBuff = BufferUtils.createFloatBuffer(16);
        vMat = new Mat4f();
        
        updateCamera();
	}
	
	public void specifyUniforms() {
        vMat.store(matBuff);
        GL20.glUniformMatrix4fv(vMatUniLoc, false, matBuff);
	}
	
	public void getUniforms() {
		vMatUniLoc = GL20.glGetUniformLocation(shaderProgramId, "view");
	}
	
	public void updateCamera() {
		Vec3f position = World.camera.getPosition();
		vMat.loadIdentity();
        vMat.rotateX(World.camera.getPitch());
        vMat.rotateY(World.camera.getYaw());
        vMat.translate(-position.x, -position.y, -position.z);
	}
	
	public void updateSkyboxCamera() {
		Vec3f position = World.camera.getPosition();
		vMat.loadIdentity();
        vMat.rotateX(World.camera.getPitch());
        vMat.rotateY(World.camera.getYaw());
        vMat.translate(-position.x/12.0f, -position.y/12.0f, -position.z/12.0f);
	}
	
	public int getProgramId() {
		return shaderProgramId;
	}
}
