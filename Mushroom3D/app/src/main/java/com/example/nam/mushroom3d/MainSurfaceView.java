package com.example.nam.mushroom3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainSurfaceView extends GLSurfaceView {

    private MainRenderer mainRenderer;

    public MainSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        mainRenderer = new MainRenderer(context);
        setRenderer(mainRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 100.0f / 320;
    private float prevX;
    private float prevY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - prevX;
                float dy = y - prevY;

                mainRenderer.setMushroomYAngle(mainRenderer.getMushroomYAngle() + dx * TOUCH_SCALE_FACTOR);
                mainRenderer.setMushroomXAngle(mainRenderer.getMushroomXAngle() + dy * TOUCH_SCALE_FACTOR);

                requestRender();
                break;
        }

        prevX = x;
        prevY = y;

        return true;
    }
}
