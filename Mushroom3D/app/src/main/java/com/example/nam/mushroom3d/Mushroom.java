package com.example.nam.mushroom3d;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Mushroom {

    private FloatBuffer vertexBuffer;
    private IntBuffer indexBuffer;
    private FloatBuffer colorBuffer;

    private final String vertexShaderCode =
                    "uniform mat4 uMatrix;" +
                    "attribute vec4 aPosition; " +
                    "attribute vec4 aColor;" +
                    "varying vec4 vColor;"+
                    "void main() {" +
                    "   vColor = aColor;" +
                    "   gl_Position = uMatrix * aPosition;" +
                    "}";
    private final String fragmentShaderCode =
                    "precision mediump float; " +
                    "varying vec4 vColor; " +
                    "void main() {" +
                    "   gl_FragColor = vColor;" +
                    "}";

    private int shaderProgram;

    float[] colors;

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    int indexLength;
    float[] vertices;

    public Mushroom(float[] verts, int[] inds) {

        float[] nVerts = new float[verts.length];

        float scale = 0.25f;
        for (int i = 0; i < verts.length; i++) {
            nVerts[i] = verts[i] * scale;
        }

        ArrayList<Float> colorsArrayList = new ArrayList<Float>();

        for (int i = 0; i < verts.length; i++) {
            colorsArrayList.add((float) Math.random());
            colorsArrayList.add((float) Math.random());
            colorsArrayList.add((float) Math.random());
            colorsArrayList.add(1.0f);
        }

        colors = new float[colorsArrayList.size()];
        for (int i = 0; i < colorsArrayList.size(); i++) {
            colors[i] = colorsArrayList.get(i);
        }


        indexLength = inds.length;
        vertices = nVerts;

        vertexBuffer = ByteBuffer.allocateDirect(nVerts.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(nVerts).position(0);

        indexBuffer = ByteBuffer.allocateDirect(inds.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        indexBuffer.put(inds).position(0);

        colorBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colors).position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);
    }

    private int mMVPMatrixHandle, mColorHandle, mPositionHandle;

    public void draw(float[] mvpMatrix) {

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMatrix");
        int mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "aPosition");
        int mColorHandle = GLES20.glGetAttribLocation(shaderProgram, "aColor");

        GLES20.glUseProgram(shaderProgram);

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 0, colorBuffer);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexLength, GLES20.GL_UNSIGNED_INT, indexBuffer);
    }

    public float[] getCentroid() {
        float xCenter = 0, yCenter = 0, zCenter = 0;
        for (int i = 0; i < vertices.length; i++) {
            xCenter += vertices[i];
            yCenter += vertices[i+1];
            zCenter += vertices[i+2];
            i+=2;
        }
        xCenter = xCenter / (vertices.length / 3);
        yCenter = yCenter / (vertices.length / 3);
        zCenter = zCenter / (vertices.length / 3);
        float[] centroid = {xCenter, yCenter, zCenter};
        return centroid;
    }

}

