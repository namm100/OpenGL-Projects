package com.example.nammy.rotatingshapes3d;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    GLSurfaceView mainSurfaceView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check version

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        boolean supportsES2 = (info.reqGlEsVersion >= 0x20000);

        if (supportsES2) {
            mainSurfaceView = new MainSurfaceView(this);
            this.setContentView(mainSurfaceView);
        } else {
            Log.e("OpenGLES 2", "Your device doesn't support ES2. (" + info.reqGlEsVersion + ")");
        }
    }

    protected void onPause() {
        super.onPause();
        mainSurfaceView.onPause();
    }


    protected void onResume() {
        super.onResume();
        mainSurfaceView.onResume();
    }
}
