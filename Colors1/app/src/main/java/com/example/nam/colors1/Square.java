package com.example.nam.colors1;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {
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

    private final float[] vertices = {
        0.2f, 0.2f, 0.0f,
        0.4f, -0.4f, 0.0f,
        -0.4f, 0.4f, 0.0f,
        -0.4f, -0.4f, 0.0f
    };

    private final int[] indices = {
        0,2,1,1,3,2
    };

    private final float[] colors = {
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,
        1.0f, 0.0f, 1.0f, 1.0f
    };

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public Square() {

        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        indexBuffer = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        indexBuffer.put(indices).position(0);

        colorBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colors).position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);

    }

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

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_INT, indexBuffer);

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

/*
GLES20.glUseProgram(shaderProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        int mColorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        //GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glUniform4fv(mColorHandle, 1, colors,0);
        //GLES20.glUniform4fv(mColorHandle, colors.length, colorBuffer);

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_INT, indexBuffer);
 */