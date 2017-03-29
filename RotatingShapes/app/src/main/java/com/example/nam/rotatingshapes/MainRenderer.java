package com.example.nam.rotatingshapes;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainRenderer implements GLSurfaceView.Renderer {

    Triangle triangle;
    Square square;

    // 1 is the triangle, 2 is the square
    private float[] mRotationMatrix1 = new float[16];
    private float[] mRotationMatrix2 = new float[16];

    private float[] mViewMatrix = new float[16];

    private float[] mvpMatrix1 = new float[16];
    private float[] mvpMatrix2 = new float[16];

    private float[] mProjectionMatrix1 = new float[16];
    private float[] mProjectionMatrix2 = new float[16];

    private float tAngle, sAngle;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        float points1[] = {
                0.0f, 0.6f, 0.0f,
                -0.2f, 0.3f, 0.0f,
                0.2f, 0.3f, 0.0f
        };
        float color1[] = new float[] { 0.0f, 1.0f, 0.0f, 1.0f };

        float points2[] = {
                -0.2f, -0.3f, 0.0f,
                -0.2f, -0.6f, 0.0f,
                0.2f, -0.6f, 0.0f,
                0.2f, -0.3f, 0.0f
        };
        // top left
        // bottom left
        // bottom right
        // top right

        float color2[] = new float[] { 0.0f, 0.0f, 1.0f , 1.0f};

        triangle = new Triangle(points1, color1);

        square = new Square(points2, color2);

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix1, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.frustumM(mProjectionMatrix2, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    public void onDrawFrame(GL10 gl) {

        float[] scratch1 = new float[16];
        float[] scratch2 = new float[16];
        // clear the screen with a background color
        GLES20.glClearColor(0.8f, 0.0f, 0.0f, 1.0f);
        // clear the colors, clear the depth
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        float[] triangleCentroid = triangle.findCentroid();
        float[] squareCentroid = square.findCentroid();

        Matrix.setLookAtM(mViewMatrix, 0,
                0.0f, 0.0f, -3,
                0f, 0.0f, 0f,
                0f, 1.0f, 0.0f);

        // first draw & rotate the triangle

        Matrix.multiplyMM(mvpMatrix1, 0, mProjectionMatrix1, 0, mViewMatrix,0);

        Matrix.translateM(mvpMatrix1, 0, triangleCentroid[0], triangleCentroid[1], triangleCentroid[2]);

        Matrix.setRotateM(mRotationMatrix1, 0, tAngle, 0.0f, 0.0f, 1.0f);

        Matrix.multiplyMM(scratch1, 0, mvpMatrix1, 0, mRotationMatrix1, 0);

        Matrix.translateM(scratch2, 0, scratch1, 0, -1 * triangleCentroid[0], -1 * triangleCentroid[1], -1 * triangleCentroid[2]);

        triangle.draw(scratch2);

        // second draw and rotate the square

        float[] scratch3 = new float[16];
        float[] scratch4 = new float[16];

        Matrix.multiplyMM(mvpMatrix2, 0, mProjectionMatrix2, 0, mViewMatrix, 0);
        Matrix.translateM(mvpMatrix2, 0, squareCentroid[0], squareCentroid[1], squareCentroid[2]);
        Matrix.setRotateM(mRotationMatrix2, 0, sAngle, 0, 0, 1.0f);

        Matrix.multiplyMM(scratch3, 0, mvpMatrix2, 0, mRotationMatrix2, 0);

        Matrix.translateM(scratch4, 0, scratch3, 0, -1 * squareCentroid[0], -1 * squareCentroid[1], -1 * squareCentroid[2]);

        square.draw(scratch4);

    }

    public float getTAngle() {
        return tAngle;
    }
    public void setTAngle(float angle) {
        tAngle = angle;
    }
    public float getSAngle() {
        return sAngle;
    }
    public void setSAngle(float angle) {
        sAngle = angle;
    }

}
