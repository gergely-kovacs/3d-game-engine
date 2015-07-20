package objects;

import java.util.ArrayList;
import java.util.List;

import utils.math.Vector2f;
import utils.math.Vector3f;

public class Model {
	public List<Vector3f> vertices = new ArrayList<Vector3f>();
	public List<Vector2f> textures = new ArrayList<Vector2f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Face> faces = new ArrayList<Face>();
	
	public Model() {
	}
	
}
