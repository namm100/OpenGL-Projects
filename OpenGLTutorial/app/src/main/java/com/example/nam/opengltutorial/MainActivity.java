package com.example.nam.opengltutorial;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if supports version of OpenGL
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        boolean supportsES2 = (info.reqGlEsVersion >= 0x20000);
        if (supportsES2) {
            // where all the drawing happens <- the renderer
            MainRenderer mainRenderer = new MainRenderer();

            // the creating the view and setting the renderer to it.
            MainSurfaceView mainSurfaceView = new MainSurfaceView(this);
            mainSurfaceView.setEGLContextClientVersion(2);
            mainSurfaceView.setRenderer(mainRenderer);

            this.setContentView(mainSurfaceView);
        } else {
            Log.e("OpenGLES 2", "Your device doesn't support ES2. (" + info.reqGlEsVersion + ")");
        }
    }
}
