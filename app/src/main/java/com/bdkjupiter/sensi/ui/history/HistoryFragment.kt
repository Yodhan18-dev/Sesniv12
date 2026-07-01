package com.bdkjupiter.sensi.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bdkjupiter.sensi.databinding.FragmentHistoryBinding
import com.bdkjupiter.sensi.utils.PrefsManager

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: PrefsManager
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PrefsManager(requireContext())

        adapter = HistoryAdapter(prefs.getHistory().toMutableList())
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        updateEmptyState()

        binding.btnClearHistory.setOnClickListener {
            prefs.clearHistory()
            adapter.clearAll()
            updateEmptyState()
        }
    }

    private fun updateEmptyState() {
        if (adapter.itemCount == 0) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvHistory.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
