package com.example.nammy.indexbufferingtutorial;


import android.opengl.GLES20;

public class Shader {

    private final static String vertcode =
                    "attribute vec4 a_pos; " +
                    "void main() {" +
                    "   gl_Position = a_pos;" +
                    "}";
    private final static String fragcode =
                    "precision mediump float; " +
                    "uniform vec4 vColor; " +
                    "void main() {" +
                    "   gl_FragColor = vColor;" +
                    "}";

    private static int program;

    public static int positionHandle, colorHandle;

    public static void makeProgram() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertcode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragcode);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, "a_pos");
        colorHandle = GLES20.glGetUniformLocation(program, "u_color");

        GLES20.glUseProgram(program);
    }

    private static int loadShader(int type, String shaderText) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderText);
        GLES20.glCompileShader(shader);
        return shader;
    }

}
