package com.example.nam.rotatingshapes;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private FloatBuffer vertexBuffer;
    private float vertices[] = new float[9];
    private float color[] = new float[4];

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

    public Triangle(float[] points, float[] color_param) {

        for (int i = 0; i < points.length; i++) {
            vertices[i] = points[i];
        }
        for (int i = 0; i < color_param.length; i++) {
            color[i] = color_param[i];
        }

        // holds value of vertices
        // note 4 is the size of a float
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);


        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // vertex shader - points of polygons. fragment shader - color

        shaderProgram = GLES20.glCreateProgram();
        // attach the shaders to the program
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        // compile the shader
        GLES20.glLinkProgram(shaderProgram);
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(shaderProgram);
        // link from the program to the application
        int positionAttrib = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionAttrib);

        // point it in the right direction
        // note the 3 = number of numbers in vertex
        GLES20.glVertexAttribPointer(positionAttrib, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        // time for the fragment shaders
        // another link
        int colorUniform = GLES20.glGetUniformLocation(shaderProgram, "vColor");

        // set color to draw triangle
        GLES20.glUniform4fv(colorUniform, 1, color,0);

        // apply transformation
        int uMatUnfirom = GLES20.glGetUniformLocation(shaderProgram, "uMatrix");
        GLES20.glUniformMatrix4fv(uMatUnfirom, 1, false, mvpMatrix, 0);

        // draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length / 3);
        // finish
        GLES20.glDisableVertexAttribArray(positionAttrib);
    }

    public float[] findCentroid() {
        // use float[] vertices
        float[] centroid = new float[3];
        centroid[0] = (vertices[0]+vertices[3]+vertices[6])/3.0f;
        centroid[1] = (vertices[1]+vertices[4]+ vertices[7])/3.0f;
        centroid[2] = (vertices[2]+vertices[5]+vertices[8])/3.0f;
        return centroid;
    }

}
