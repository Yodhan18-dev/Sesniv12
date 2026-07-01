package com.bdkjupiter.sensi.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bdkjupiter.sensi.R
import com.bdkjupiter.sensi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        binding.cardDev.startAnimation(slideAnim)

        binding.btnYoutube.setOnClickListener {
            openUrl("https://youtube.com/@bdk-jupiter?si=_tPIvIbQ_4aF35GA")
        }
        binding.btnInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/bdk_jupiter_ff?igsh=MWZlNnRrNGdoMGkyMw==")
        }
        binding.btnDiscord.setOnClickListener {
            openUrl("https://discord.gg/ztTfSavsf")
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
