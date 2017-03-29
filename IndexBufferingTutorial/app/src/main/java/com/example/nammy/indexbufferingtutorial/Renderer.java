package com.example.nammy.indexbufferingtutorial;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private FloatBuffer vertBuffer;

    float[] verts = {
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
    };

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Shader.makeProgram();
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);



        vertBuffer = makeFloatBuffer(verts);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    public void onDrawFrame(GL10 gl) {


        GLES20.glClearColor(1.0f, 1.0f,1.0f, 1.0f);
        GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);

        GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    public FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer floatBuffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer.put(array).position(0);
        return floatBuffer;
    }
}
