package objects;

import utils.maths.Vec3f;

public class Face {
	public Vec3f vertexIndices = new Vec3f();
	public Vec3f textureIndices = new Vec3f();
	public Vec3f normalIndices = new Vec3f();
	
	public Face(Vec3f vertexIndices, Vec3f textureIndices, Vec3f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.textureIndices = textureIndices;
		this.normalIndices = normalIndices;
	}
	
	public String toString(int vertexNumber) {
		switch(vertexNumber) {
		case 1:
			return "" + this.vertexIndices.x + "/" + this.textureIndices.x + "/" + this.normalIndices.x;
		case 2:
			return "" + this.vertexIndices.y + "/" + this.textureIndices.y + "/" + this.normalIndices.y;
		case 3:
			return "" + this.vertexIndices.z + "/" + this.textureIndices.z + "/" + this.normalIndices.z;
		default:
			System.out.println("Error");
			break;
		}
		return "Invalid";
	}
}
