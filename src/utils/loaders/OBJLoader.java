package utils.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utils.Face;
import utils.Model;
import utils.math.Vector2f;
import utils.math.Vector3f;

public class OBJLoader {
	
	public static Model loadModel(File f) {
		BufferedReader bf;
		String line;
		Model m = new Model();
		try {
			bf = new BufferedReader(new FileReader(f));
			while ((line = bf.readLine()) != null) {
				if(line.startsWith("v ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					float z = Float.valueOf(line.split(" ")[3]);
					m.vertices.add(new Vector3f(x, y, z));
				} else if (line.startsWith("vt ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					m.textures.add(new Vector2f(x, y));
				} else if (line.startsWith("vn ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					float z = Float.valueOf(line.split(" ")[3]);
					m.normals.add(new Vector3f(x, y, z));
				} else if (line.startsWith("f ")) {
					Vector3f vertexIndices =
						new Vector3f(
							Float.valueOf(line.split(" ")[1].split("/")[0]),
							Float.valueOf(line.split(" ")[2].split("/")[0]),
							Float.valueOf(line.split(" ")[3].split("/")[0]));
					Vector3f normalIndices =
						new Vector3f(
							Float.valueOf(line.split(" ")[1].split("/")[2]),
							Float.valueOf(line.split(" ")[2].split("/")[2]),
							Float.valueOf(line.split(" ")[3].split("/")[2]));
					m.faces.add(new Face(vertexIndices, normalIndices));
				}
				else continue;
			}
			bf.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} 
		
		return m;
	}
}
