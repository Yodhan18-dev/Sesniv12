package com.bdkjupiter.sensi.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bdkjupiter.sensi.R
import com.bdkjupiter.sensi.databinding.ActivitySplashBinding
import com.bdkjupiter.sensi.ui.login.LoginActivity
import com.bdkjupiter.sensi.ui.home.MainActivity
import com.bdkjupiter.sensi.utils.PrefsManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var prefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PrefsManager(this)

        // Logo zoom animation
        val zoomAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        binding.ivLogo.startAnimation(zoomAnim)

        // Glow pulse animation
        val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.pulse_glow)
        binding.tvAppName.startAnimation(pulseAnim)

        // Particle effect view
        binding.particleView.startParticles()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (prefs.isLoggedIn()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 3000)
    }
}
