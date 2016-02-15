package objects;

import util.maths.Vec3f;

public class Face {
	public Vec3f vertexIndices = new Vec3f();
	public Vec3f textureIndices = new Vec3f();
	public Vec3f normalIndices = new Vec3f();
	
	public Face(Vec3f vertexIndices, Vec3f textureIndices, Vec3f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.textureIndices = textureIndices;
		this.normalIndices = normalIndices;
	}
	
	public Face(Vec3f vertexIndices, Vec3f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
	}
	
	public String toString(int vertexNumber) {
		switch(vertexNumber) {
		case 1:
			return "" + (int) this.vertexIndices.x + "/" + (int) this.textureIndices.x + "/" + (int) this.normalIndices.x;
		case 2:
			return "" + (int) this.vertexIndices.y + "/" + (int) this.textureIndices.y + "/" + (int) this.normalIndices.y;
		case 3:
			return "" + (int) this.vertexIndices.z + "/" + (int) this.textureIndices.z + "/" + (int) this.normalIndices.z;
		default:
			System.out.println("Error in converting the face to string");
			return null;
		}
	}
}
