package com.evangelidis.loceat.splashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.evangelidis.loceat.Constant.SPLASHSCREEN_TIME
import com.evangelidis.loceat.R
import com.evangelidis.loceat.location.LocationActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(LocationActivity.createIntent(this))
            finish()
        }, SPLASHSCREEN_TIME)
    }

    override fun onBackPressed() {}
}