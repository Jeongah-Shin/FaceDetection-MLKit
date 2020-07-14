package com.jeongari.facedetection_mlkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeongari.facedetection_mlkit.camera.Camera2BasicFragment
import kotlinx.android.synthetic.main.activity_main.*

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (null == savedInstanceState) {
            fragmentManager
                .beginTransaction()
                .replace(R.id.container, Camera2BasicFragment.newInstance())
                .commit()
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
