package com.example.nam.opengltutorial;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainRenderer implements GLSurfaceView.Renderer {

    Triangle triangle;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        float points[] = {0.0f, 1.0f, 0.0f,
                -1.0f, 0.5f, 0.0f,
                1.0f, 0.5f, 0.0f };
        float color[] = new float[] { 0.66f, 0.408f, 0.212f, 1.0f };
        triangle = new Triangle(points, color);

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    public void onDrawFrame(GL10 gl) {

        // clear the screen with a background color
        GLES20.glClearColor(0.8f, 0.0f, 0.0f, 1.0f);
        // clear the colors, clear the depth
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // draw the triangle
        triangle.draw();


    }
}
