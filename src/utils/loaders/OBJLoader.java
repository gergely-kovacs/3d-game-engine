package utils.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import objects.Face;
import objects.Model;
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
					Vector3f vertex = new Vector3f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]),
						Float.valueOf(line.split(" ")[3]));
					m.vertices.add(vertex);
				}
				
				else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]));
					m.textures.add(texture);
				}
				
				else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]),
						Float.valueOf(line.split(" ")[3]));
					m.normals.add(normal);
				}
				
				else if (line.startsWith("f ")) {
					Vector3f vertexIndices = new Vector3f(
						Float.valueOf(line.split(" ")[1].split("/")[0]),
						Float.valueOf(line.split(" ")[2].split("/")[0]),
						Float.valueOf(line.split(" ")[3].split("/")[0]));
					
					Vector3f normalIndices = new Vector3f(
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
