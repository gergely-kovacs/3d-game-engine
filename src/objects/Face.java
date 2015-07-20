package objects;

import utils.math.Vector3f;

public class Face {
	public Vector3f vertexIndices = new Vector3f();
	public Vector3f normalIndices = new Vector3f();
	
	public Face(Vector3f vertexIndices, Vector3f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
	}
	
	public Face(Vector3f vertexIndices) {
		this.vertexIndices = vertexIndices;
	}
}
