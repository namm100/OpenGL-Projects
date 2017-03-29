package com.example.nammy.rotatingshapes3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class MainSurfaceView extends GLSurfaceView {

    MainRenderer mainRenderer;

    public MainSurfaceView(Context context) {

        super(context);

        setEGLContextClientVersion(2);

        mainRenderer = new MainRenderer();
        setRenderer(mainRenderer);


        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    private float prevX, prevY;
    private final float TOUCH_SCALE_FACTOR = 100.0f / 320;

    public boolean onTouchEvent(MotionEvent event) {

        float mX = event.getX();
        float mY = event.getY();

        float maxX = getWidth();
        float maxY = getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:


                float dx = mX - prevX;
                float dy = mY - prevY;

                //Rotate along zed axis

                // reverse direction of rotation above the mid-line
                if (mY > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (mX < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                float newZedAngle = mainRenderer.getCubeXAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR);
                mainRenderer.setCubeXAngle(newZedAngle);  // = 180.0f / 320

                float dx1 = mX - prevX;

                // rotate along x

                float newXAngle = mainRenderer.getCubeXAngle() + TOUCH_SCALE_FACTOR;

                if (dx1 > 0) {
                    newXAngle *= 1;
                } else if (dx1 < 0) {
                    newXAngle *= -1;
                }

                //mainRenderer.setCubeXAngle(newXAngle);

                requestRender();
                break;
        }

        prevX = mX;
        prevY = mY;
        return true;
    }
}
