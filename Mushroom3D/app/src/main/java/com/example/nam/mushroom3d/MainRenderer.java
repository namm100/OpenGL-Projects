package com.example.nam.mushroom3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainRenderer implements GLSurfaceView.Renderer {


    float[] mushroomVertices;
    int[] mushroomIndices;

    private float[] mViewMatrix = new float[16];

    private float[] mushroomXRotationMatrix = new float[16];
    private float[] mushroomYRotationMatrix = new float[16];
    private float[] mushroomZRotationMatrix = new float[16];

    private float[] mushroomProjectionMatrix = new float[16];
    private float[] mushroomMVPMatrix = new float[16];

    public float getMushroomXAngle() {
        return mushroomXAngle;
    }

    public void setMushroomXAngle(float mushroomXAngle) {
        this.mushroomXAngle = mushroomXAngle;
    }

    private float mushroomXAngle = 0.0f;

    public float getMushroomYAngle() {
        return mushroomYAngle;
    }

    public void setMushroomYAngle(float mushroomYAngle) {
        this.mushroomYAngle = mushroomYAngle;
    }

    private float mushroomYAngle = 0.0f;

    public float getMushroomZAngle() {
        return mushroomZAngle;
    }

    public void setMushroomZAngle(float mushroomZAngle) {
        this.mushroomZAngle = mushroomZAngle;
    }

    private float mushroomZAngle = 0.0f;

    float[] backgroundColor = {0f,0f,0f,1.0f};
    Mushroom mushroom; float[] mushroomCentroid;


    public MainRenderer(Context context) {

        BlenderParser blenderParser = new BlenderParser("MushroomTutorial.obj");
        try {
            blenderParser.parseObjectFile(context);
            mushroomVertices = blenderParser.getVertices();
            mushroomIndices = blenderParser.getIndices();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mushroom = new Mushroom(mushroomVertices, mushroomIndices);
        mushroomCentroid = mushroom.getCentroid();


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mushroomProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // draw the mushroom
        Matrix.multiplyMM(mushroomMVPMatrix, 0, mushroomProjectionMatrix, 0, mViewMatrix,0);

        // translate here posiive centroid
        Matrix.translateM(mushroomMVPMatrix, 0, mushroomCentroid[0], mushroomCentroid[1], mushroomCentroid[2]);

        Matrix.setRotateM(mushroomXRotationMatrix, 0, mushroomXAngle, 1.0f, 0.0f, 0.0f);
        Matrix.setRotateM(mushroomYRotationMatrix, 0, mushroomYAngle, 0.0f, 1.0f, 0.0f);
        Matrix.setRotateM(mushroomZRotationMatrix, 0, mushroomZAngle, 0.0f, 0.0f, 1.0f);

        Matrix.multiplyMM(mushroomMVPMatrix, 0, mushroomMVPMatrix, 0, mushroomZRotationMatrix, 0);
        Matrix.multiplyMM(mushroomMVPMatrix, 0, mushroomMVPMatrix, 0, mushroomYRotationMatrix, 0);
        Matrix.multiplyMM(mushroomMVPMatrix, 0, mushroomMVPMatrix, 0, mushroomXRotationMatrix, 0);

        // translate here negative centroid
        Matrix.translateM(mushroomMVPMatrix, 0, -1.0f * mushroomCentroid[0], -1.0f * mushroomCentroid[1], -1.0f * mushroomCentroid[2]);

        // scale the matrix
        // Matrix.scaleM(mushroomMVPMatrix, 0, 0.5f, 0.5f, 0.5f);

        mushroom.draw(mushroomMVPMatrix);

        // change the background
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.00040f * ((int) time);
        backgroundColor[0] = (float) Math.cos(angle);
        backgroundColor[1] = (float) Math.sin(angle);
    }
}
