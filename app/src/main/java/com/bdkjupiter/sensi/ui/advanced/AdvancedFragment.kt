package com.bdkjupiter.sensi.ui.advanced

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bdkjupiter.sensi.R
import com.bdkjupiter.sensi.databinding.FragmentAdvancedBinding
import com.bdkjupiter.sensi.utils.PrefsManager
import com.bdkjupiter.sensi.utils.SoundManager
import kotlin.math.roundToInt

class AdvancedFragment : Fragment() {

    private var _binding: FragmentAdvancedBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: PrefsManager
    private lateinit var sound: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PrefsManager(requireContext())
        sound = SoundManager(requireContext())

        binding.radio50.isChecked = true
        updateCardHighlight(50)

        binding.card50.setOnClickListener {
            binding.radio50.isChecked = true
            binding.radio70.isChecked = false
            updateCardHighlight(50)
        }
        binding.card70.setOnClickListener {
            binding.radio70.isChecked = true
            binding.radio50.isChecked = false
            updateCardHighlight(70)
        }
        binding.radio50.setOnClickListener { binding.radio70.isChecked = false; updateCardHighlight(50) }
        binding.radio70.setOnClickListener { binding.radio50.isChecked = false; updateCardHighlight(70) }
        binding.btnApplyBoost.setOnClickListener { applyBoost() }
        binding.btnCopyBoost.setOnClickListener { copyBoostedSettings() }
    }

    private fun updateCardHighlight(percent: Int) {
        binding.card50.cardElevation = if (percent == 50) 12f else 4f
        binding.card70.cardElevation = if (percent == 70) 12f else 4f
    }

    private fun applyBoost() {
        val lastRecord = prefs.getLastRecord()
        if (lastRecord == null) {
            Toast.makeText(requireContext(), "⚠️ Generate your base sensitivity first!", Toast.LENGTH_LONG).show()
            sound.playNo()
            return
        }
        val boostPercent = if (binding.radio50.isChecked) 50 else 70
        val multiplier = 1.0 + (boostPercent / 100.0)
        fun boost(value: Int): Int = (value * multiplier).roundToInt().coerceAtMost(500)

        binding.tvBoostTitle.text = "🎮 BOOSTED SETTINGS (+$boostPercent%)"
        binding.tvBGeneral.text = "${boost(lastRecord.general)}"
        binding.tvBRedDot.text = "${boost(lastRecord.redDot)}"
        binding.tvB2x.text = "${boost(lastRecord.scope2x)}"
        binding.tvB4x.text = "${boost(lastRecord.scope4x)}"
        binding.tvBSniper.text = "${boost(lastRecord.sniper)}"
        binding.tvBDpi.text = "${boost(lastRecord.dpi)}"
        binding.tvBoostNote.text = "⚡ Based on ${lastRecord.mobileName} ${lastRecord.mobileModel} — $boostPercent% boost applied."

        binding.cardBoostResult.visibility = View.VISIBLE
        binding.cardBoostResult.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up))
        sound.playGenerate()
    }

    private fun copyBoostedSettings() {
        val text = "🚀 BDK JUPITER BOOSTED SENSI\n" +
            "General: ${binding.tvBGeneral.text}\n" +
            "Red Dot: ${binding.tvBRedDot.text}\n" +
            "2x Scope: ${binding.tvB2x.text}\n" +
            "4x Scope: ${binding.tvB4x.text}\n" +
            "Sniper: ${binding.tvBSniper.text}\n" +
            "DPI: ${binding.tvBDpi.text}"
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("BDK Boosted Sensi", text))
        sound.playClick()
        Toast.makeText(requireContext(), "Boosted settings copied! 🚀", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
