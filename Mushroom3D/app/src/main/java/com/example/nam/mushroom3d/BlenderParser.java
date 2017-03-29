package com.example.nam.mushroom3d;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BlenderParser {

    String filePath;
    float[] vertices;
    int[] indices;
    ArrayList<Float> verts;
    ArrayList<Integer> inds;

    public BlenderParser(String filePath) {
        this.filePath = filePath;

        verts = new ArrayList<Float>();
        inds = new ArrayList<Integer>();
    }

    public void parseObjectFile(Context context) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("MushroomTutorial.obj"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
                    inds.add((int) elemI - 1);
                }
            }

        }

        bufferedReader.close();
        inputStreamReader.close();
        vertices = new float[verts.size()];
        indices = new int[inds.size()];

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
    public int[] getIndices() {
        return indices;
    }
}

