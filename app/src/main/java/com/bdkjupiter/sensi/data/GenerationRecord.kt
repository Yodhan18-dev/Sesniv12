package com.bdkjupiter.sensi.data

data class GenerationRecord(
    val id: String = System.currentTimeMillis().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val userName: String,
    val mobileName: String,
    val mobileModel: String,
    val ram: Int,
    val storage: Int,
    val mobileAge: Int,
    val followedBdk: Boolean,
    // Results
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val dpi: Int,
    val fireButton: Int
) {
    fun toShareText(): String {
        return """
╔══════════════════════╗
  🎮 BDK JUPITER SENSI
╚══════════════════════╝

👤 Player: $userName
📱 Device: $mobileName $mobileModel
💾 RAM: ${ram}GB | Storage: ${storage}GB

━━━━━━━ SETTINGS ━━━━━━━
🔴 General Sensitivity: $general
🔵 Red Dot: $redDot
🔭 2x Scope: $scope2x
🔬 4x Scope: $scope4x
🎯 Sniper Scope: $sniper
🔥 Fire Button: $fireButton
📐 DPI (Smallest Width): $dpi

⚠️ Keep practicing for 2 weeks to set ur hands on the sensi

💬 Join Discord: https://discord.gg/ztTfSavsf
📺 YouTube: @bdk-jupiter
        """.trimIndent()
    }
}
