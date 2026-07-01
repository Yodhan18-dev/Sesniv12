package com.bdkjupiter.sensi.ui.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bdkjupiter.sensi.R
import com.bdkjupiter.sensi.databinding.ActivityLoginBinding
import com.bdkjupiter.sensi.ui.home.MainActivity
import com.bdkjupiter.sensi.utils.PrefsManager
import com.bdkjupiter.sensi.utils.SoundManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: PrefsManager
    private lateinit var sound: SoundManager

    // 100 fixed BDK passwords
    private val validPasswords = setOf(
        "BDK-ASXD","BDK-QWRT","BDK-ZMNP","BDK-KLXA","BDK-TYUI",
        "BDK-HJKE","BDK-PQAZ","BDK-WXCV","BDK-RFVT","BDK-NBGM",
        "BDK-DKLP","BDK-UYTR","BDK-MXQW","BDK-ZPLK","BDK-GHFD",
        "BDK-CVBN","BDK-AQWS","BDK-EDRF","BDK-TGBY","BDK-UJMI",
        "BDK-IKOL","BDK-PMNB","BDK-LKJH","BDK-XZQA","BDK-VTRE",
        "BDK-BNML","BDK-CXZA","BDK-FGHJ","BDK-JKLP","BDK-QAZX",
        "BDK-WSXC","BDK-EDCV","BDK-RFVB","BDK-TGBN","BDK-YHNM",
        "BDK-UJMK","BDK-IKLP","BDK-OLPA","BDK-ZXCV","BDK-ASQW",
        "BDK-POIU","BDK-MNBV","BDK-LTRE","BDK-KJHG","BDK-HYTR",
        "BDK-GFDS","BDK-FVBN","BDK-DCXZ","BDK-SWER","BDK-QPLM",
        "BDK-ZRTY","BDK-XMKO","BDK-CVGH","BDK-BNJK","BDK-NMQW",
        "BDK-MZXC","BDK-LPOI","BDK-KMNB","BDK-JTRE","BDK-HCVB",
        "BDK-GQAZ","BDK-FWSX","BDK-DERT","BDK-SFGH","BDK-AJKL",
        "BDK-QWER","BDK-WRTY","BDK-ERUI","BDK-RTOP","BDK-TYAS",
        "BDK-YUIK","BDK-UIZX","BDK-IOCV","BDK-OPBN","BDK-PALK",
        "BDK-LMZX","BDK-KJCV","BDK-JHBN","BDK-HGFD","BDK-GTRE",
        "BDK-FQWE","BDK-DASX","BDK-SZXC","BDK-AXCV","BDK-QBNM",
        "BDK-WMKO","BDK-EPLA","BDK-RJHG","BDK-TCVB","BDK-YNMQ",
        "BDK-UKLP","BDK-IMNB","BDK-OXCV","BDK-PQWE","BDK-LRTY",
        "BDK-KUIO","BDK-JASD","BDK-HZXC","BDK-GBNM","BDK-FPLK"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PrefsManager(this)
        sound = SoundManager(this)

        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        binding.cardLogin.startAnimation(slideIn)

        binding.btnLogin.setOnClickListener {
            val entered = binding.etPassword.text.toString().trim().uppercase()
            handleLogin(entered)
        }
    }

    private fun fetchDeviceId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
    }

    private fun handleLogin(password: String) {
        if (password.isEmpty()) {
            showError("Enter your BDK key!")
            return
        }
        if (!validPasswords.contains(password)) {
            sound.playNo()
            showError("Invalid BDK key! Contact BDK JUPITER.")
            binding.etPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
            return
        }

        val deviceId = fetchDeviceId()
        val lockedDevice = prefs.getLockedDevice(password)

        when {
            lockedDevice == null -> {
                // First use — lock to this device
                prefs.lockPasswordToDevice(password, deviceId)
                loginSuccess(password)
            }
            lockedDevice == deviceId -> {
                loginSuccess(password)
            }
            else -> {
                sound.playNo()
                showError("This key is already used on another device!")
                binding.etPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
            }
        }
    }

    private fun loginSuccess(password: String) {
        sound.playYes()
        prefs.setLoggedIn(true, password)
        binding.tvError.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
    }
}
