package com.example.nam.rotatingshapes;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainSurfaceView extends GLSurfaceView {

    private MainRenderer mainRenderer;
    public MainSurfaceView(Context context) {

        super(context);

        setEGLContextClientVersion(2);

        mainRenderer = new MainRenderer();
        setRenderer(mainRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    private float prevX, prevY;
    private final float TOUCH_SCALE_FACTOR = 1.0f/ 10;
    public boolean onTouchEvent(MotionEvent event) {
        float mX = event.getX();
        float mY = event.getY();

        float maxX = getWidth();
        float maxY = getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = mX - prevX;
                float dy = mY - prevY;

                if (mY < maxY/2) {

                    // rotate the triangle
                    if (mX < maxX / 2) {
                        dy = dy * -1;
                    }
                    if (mY > maxY/4) {
                        dx = dx * -1;
                    }
                    mainRenderer.setTAngle(mainRenderer.getTAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));

                } else {

                    // rotate the square

                    if (mX < maxX/2) {
                        dy = dy * 1;
                    }
                    if (mY > maxY * (3.0f/4.0f)) {
                        dx = dx * -1;
                    }
                    mainRenderer.setSAngle(mainRenderer.getSAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));


                }

                requestRender();

        }

        prevX = mX;
        prevY = mY;

        return true;
    }
}
