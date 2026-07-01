package com.bdkjupiter.sensi.ui.history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdkjupiter.sensi.data.GenerationRecord
import com.bdkjupiter.sensi.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val records: MutableList<GenerationRecord>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val r = records[position]
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        with(holder.binding) {
            tvHistoryUser.text = "👤 ${r.userName}"
            tvHistoryDevice.text = "📱 ${r.mobileName} ${r.mobileModel} | ${r.ram}GB RAM"
            tvHistoryDate.text = sdf.format(Date(r.timestamp))
            tvHistoryGeneral.text = "General: ${r.general}  |  Red Dot: ${r.redDot}"
            tvHistory2x4x.text = "2x: ${r.scope2x}  |  4x: ${r.scope4x}  |  Sniper: ${r.sniper}"
            tvHistoryDpi.text = "DPI: ${r.dpi}  |  Fire: ${r.fireButton}"

            btnCopyHistory.setOnClickListener {
                val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("BDK Sensitivity", r.toShareText())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(it.context, "Copied! 🔥", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = records.size

    fun clearAll() {
        records.clear()
        notifyDataSetChanged()
    }
}
