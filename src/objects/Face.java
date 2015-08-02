package objects;

import utils.maths.Vec3f;

public class Face {
	public Vec3f vertexIndices = new Vec3f();
	public Vec3f normalIndices = new Vec3f();
	public Vec3f textureIndices = new Vec3f();
	
	public Face(Vec3f vertexIndices, Vec3f normalIndices, Vec3f textureIndices) {
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
		this.textureIndices = textureIndices;
	}
	
	public Face(Vec3f vertexIndices, Vec3f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
	}
	
	public Face(Vec3f vertexIndices) {
		this.vertexIndices = vertexIndices;
	}
}
