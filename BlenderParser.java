// THIS PARSER WORKS

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BlenderParser {
	
	String filePath;
	float[] vertices, indices;
	ArrayList<Float> verts, inds;
	
	public BlenderParser(String filePath) {
		this.filePath = filePath;
		
		verts = new ArrayList<Float>();
		inds = new ArrayList<Float>();
	}
	
	public void parseObjectFile() throws IOException {

		
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
		
			if (line.charAt(0) == 'v' && line.charAt(1) == ' ') {
				String[] elem = line.substring(2).split("\\s");
				for (int i = 0; i < elem.length; i++) {
					float elemI = Float.parseFloat(elem[i]);
					verts.add(elemI);
				}
			}
			
			if (line.charAt(0) == 'f' && line.charAt(1) == ' ') {
				String nLine = line.substring(2);
				String[] bigGroup = nLine.split("\\s");
				for (String s: bigGroup) {
					String[] smallGroup = s.split("/");
					float elemI = Float.parseFloat(smallGroup[0]);
					inds.add(elemI);
				}
			}
			
		}
		fileReader.close();
		
		vertices = new float[verts.size()];
		indices = new float[inds.size()];
		
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = verts.get(i);
		}
		
		for (int i = 0; i < indices.length; i++) {
			indices[i] = inds.get(i);
		}
	}

	
	public void printArrs() {
		System.out.println("Vertices:");
		for (int i = 0; i < vertices.length; i++) {
			System.out.println(vertices[i]);
			
		}
		
		System.out.println("\nIndices:");

		for (int i = 0; i < indices.length; i++) {
			System.out.println(indices[i]);
		}
	}
	
	public float[] getVertices() {
		return vertices;
	}
	public float[] getIndices() {
		return indices;
	}
}

