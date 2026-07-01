package com.bdkjupiter.sensi.ui.generate

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
import com.bdkjupiter.sensi.data.GenerationRecord
import com.bdkjupiter.sensi.databinding.FragmentGenerateBinding
import com.bdkjupiter.sensi.utils.PrefsManager
import com.bdkjupiter.sensi.utils.SensitivityCalculator
import com.bdkjupiter.sensi.utils.SoundManager

class GenerateFragment : Fragment() {

    private var _binding: FragmentGenerateBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: PrefsManager
    private lateinit var sound: SoundManager
    private var lastRecord: GenerationRecord? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenerateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PrefsManager(requireContext())
        sound = SoundManager(requireContext())

        binding.cardForm.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up))
        binding.cardResult.visibility = View.GONE

        binding.btnGenerate.setOnClickListener {
            generateSensitivity()
        }

        binding.btnCopyAll.setOnClickListener {
            lastRecord?.let { record ->
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("BDK Sensitivity", record.toShareText())
                clipboard.setPrimaryClip(clip)
                sound.playClick()
                Toast.makeText(requireContext(), "Copied to clipboard! 🎮", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateSensitivity() {
        val userName = binding.etUsername.text.toString().trim()
        val mobileName = binding.etMobileName.text.toString().trim()
        val mobileModel = binding.etMobileModel.text.toString().trim()
        val ramStr = binding.etRam.text.toString().trim()
        val storageStr = binding.etStorage.text.toString().trim()
        val ageStr = binding.etMobileAge.text.toString().trim()

        if (userName.isEmpty() || mobileName.isEmpty() || mobileModel.isEmpty()
            || ramStr.isEmpty() || storageStr.isEmpty() || ageStr.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            sound.playNo()
            return
        }

        val ram = ramStr.toIntOrNull() ?: run {
            Toast.makeText(requireContext(), "Invalid RAM value", Toast.LENGTH_SHORT).show()
            return
        }
        val storage = storageStr.toIntOrNull() ?: run {
            Toast.makeText(requireContext(), "Invalid storage value", Toast.LENGTH_SHORT).show()
            return
        }
        val age = ageStr.toIntOrNull() ?: 0
        val followed = binding.cbFollowed.isChecked

        val brand = SensitivityCalculator.getBrand(mobileName)
        val result = SensitivityCalculator.calculate(brand, ram, storage)

        val record = GenerationRecord(
            userName = userName,
            mobileName = mobileName,
            mobileModel = mobileModel,
            ram = ram,
            storage = storage,
            mobileAge = age,
            followedBdk = followed,
            general = result.general,
            redDot = result.redDot,
            scope2x = result.scope2x,
            scope4x = result.scope4x,
            sniper = result.sniper,
            dpi = result.dpi,
            fireButton = result.fireButton
        )

        lastRecord = record
        prefs.saveHistory(record)
        sound.playGenerate()

        displayResult(record)
    }

    private fun displayResult(r: GenerationRecord) {
        binding.tvResultUsername.text = "👤 ${r.userName}"
        binding.tvResultDevice.text = "📱 ${r.mobileName} ${r.mobileModel}"
        binding.tvResultRam.text = "💾 ${r.ram}GB RAM | ${r.storage}GB Storage"
        binding.tvGeneral.text = "${r.general}"
        binding.tvRedDot.text = "${r.redDot}"
        binding.tv2x.text = "${r.scope2x}"
        binding.tv4x.text = "${r.scope4x}"
        binding.tvSniper.text = "${r.sniper}"
        binding.tvFireButton.text = "${r.fireButton}"
        binding.tvDpi.text = "${r.dpi}"

        binding.cardResult.visibility = View.VISIBLE
        binding.cardResult.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up))
        binding.scrollView.post {
            binding.scrollView.smoothScrollTo(0, binding.cardResult.top)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
