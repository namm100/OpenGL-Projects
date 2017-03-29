package com.example.nammy.rotatingshapes3d;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Pyramid {

    float[] vertices = {
            -1 * 0.25f, 0.0f, -1 * 0.25f, //1
            0.25f, 0.0f, -1 * 0.25f, //2
            0.25f, 0.0f, 0.25f,// 3
            -1* 0.25f, 0.0f, 0.25f, // 4
            0.0f, 0.25f, 0.0f // 5
    };

    float[] color = {
        101.0f/255.0f, 150.0f/255.0f, 150.0f/225.0f, 1.0f
    };

    byte[] indices = {
            1, 4, 5,
            5, 2, 1,
            1, 4, 2,
            2, 4, 3,
            3, 4, 5,
            5, 3, 2

    };

    FloatBuffer vertexBuffer;
    ByteBuffer indexBuffer;

    private final String vertexShaderCode =
            "uniform mat4 uMatrix;" +
                    "attribute vec4 vPosition; " +
                    "void main() {" +
                    " gl_Position = uMatrix * vPosition;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float; " +
                    "uniform vec4 vColor; " +
                    "void main() {" +
                    " gl_FragColor = vColor;" +
                    "}";

    private int shaderProgram;

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public Pyramid() {

        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        indexBuffer = ByteBuffer.allocateDirect(indices.length).order(ByteOrder.nativeOrder());
        indexBuffer.put(indices).position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);
    }

    private int mMVPMatrixHandle, mColorHandle, mPositionHandle;

    public void draw(float[] mvpMatrix) {

        GLES20.glUseProgram(shaderProgram);

        mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        mColorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 18, GLES20.GL_UNSIGNED_BYTE, indexBuffer);
    }
}
