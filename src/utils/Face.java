package utils;

import utils.math.Vector3f;

public class Face {
	public Vector3f vertices = new Vector3f();
	public Vector3f normals = new Vector3f();
	
	public Face(Vector3f vertices, Vector3f normals) {
		this.vertices = vertices;
		this.normals = normals;
	}
}
