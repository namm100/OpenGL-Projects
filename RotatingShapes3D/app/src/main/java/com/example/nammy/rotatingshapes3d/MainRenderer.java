package com.example.nammy.rotatingshapes3d;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainRenderer implements GLSurfaceView.Renderer {


    private float[] mViewMatrix = new float[16];

    private float[] cubeXRotationMatrix = new float[16];
    private float[] cubeYRotationMatrix = new float[16];
    private float[] cubeZRotationMatrix = new float[16];

    private float[] cubeProjectionMatrix = new float[16];
    private float[] cubeMVPMatrix = new float[16];

    private float[] pyramidProjectionMatrix = new float[16];
    private float[] pyramidMVPMatrix = new float[16];

    private float[] pyramidZRotationMatrix = new float[16];


    Cube cube;
    Pyramid pyramid;

    private float cubeXAngle =0.0f, cubeYAngle =0.0f, cubeZAngle=0.0f;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        cube = new Cube();
        pyramid = new Pyramid();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(cubeProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.frustumM(pyramidProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0,
                3.0f, 3.0f, -3,
                0f, 0.0f, 0f,
                0.0f, 1.0f, 0.0f);

        // start to draw the cube

        Matrix.multiplyMM(cubeMVPMatrix, 0, cubeProjectionMatrix, 0, mViewMatrix,0);

        // move the cube up

        Matrix.translateM(cubeMVPMatrix, 0, 0.0f, 0.7f, 0.0f);
        Matrix.setRotateM(cubeXRotationMatrix, 0, cubeXAngle, 0.0f, 1 / (float) Math.sqrt(2), 1 / (float) Math.sqrt(2));
        Matrix.setRotateM(cubeYRotationMatrix, 0, cubeYAngle, 0.0f, 1.0f, 0.0f);
        Matrix.setRotateM(cubeZRotationMatrix, 0, cubeZAngle, 0.0f, 0.0f, 1.0f);

        Matrix.multiplyMM(cubeMVPMatrix, 0, cubeMVPMatrix, 0, cubeZRotationMatrix, 0);
        Matrix.multiplyMM(cubeMVPMatrix, 0, cubeMVPMatrix, 0, cubeYRotationMatrix, 0);
        Matrix.multiplyMM(cubeMVPMatrix, 0, cubeMVPMatrix, 0, cubeXRotationMatrix, 0);

        cube.draw(cubeMVPMatrix);

        // end drawing the cube

        // start drawing the pyramid

        Matrix.multiplyMM(pyramidMVPMatrix, 0, pyramidProjectionMatrix, 0, mViewMatrix, 0);

        // move the pyramid down

        Matrix.translateM(pyramidMVPMatrix, 0, 0.0f, -0.7f, 0.0f);

        pyramid.draw(pyramidMVPMatrix);


    }

    public void setCubeXAngle(float angle) {
        cubeXAngle = angle;
    }
    public float getCubeXAngle() {
        return cubeXAngle;
    }
    public void setCubeYAngle(float angle) {
        cubeYAngle = angle;
    }
    public float getCubeYAngle() {
        return cubeYAngle;
    }
    public void setCubeZAngle(float angle) {
        cubeZAngle = angle;
    }
    public float getCubeZAngle() {
        return cubeZAngle;
    }
}
