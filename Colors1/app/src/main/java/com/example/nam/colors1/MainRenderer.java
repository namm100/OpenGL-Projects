package com.example.nam.colors1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainRenderer implements GLSurfaceView.Renderer{

    Square square;

    private float[] mViewMatrix = new float[16];
    private float[] squareProjectionMatrix = new float[16];
    private float[] squareMVPMatrix = new float[16];

    public MainRenderer(Context context) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(squareProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //Matrix.setLookAtM(mViewMatrix, 0,    0, 0, 1.5f,    0f, 0f, -5.0f,    0f, 1.0f, 0.0f);


        Matrix.multiplyMM(squareMVPMatrix, 0, squareProjectionMatrix, 0, mViewMatrix,0);

        square.draw(squareMVPMatrix);
    }
}
