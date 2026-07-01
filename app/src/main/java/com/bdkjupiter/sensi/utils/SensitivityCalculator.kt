package com.bdkjupiter.sensi.utils

object SensitivityCalculator {

    data class SensResult(
        val general: Int,
        val redDot: Int,
        val scope2x: Int,
        val scope4x: Int,
        val sniper: Int,
        val dpi: Int,
        val fireButton: Int
    )

    enum class Brand {
        REDMI_POCO_XIAOMI,
        VIVO_OPPO,
        IQOO,
        ONEPLUS_SAMSUNG,
        INFINIX_REALME
    }

    fun getBrand(mobileName: String): Brand {
        val name = mobileName.lowercase()
        return when {
            name.contains("redmi") || name.contains("poco") || name.contains("xiaomi") || name.contains("mi ") ->
                Brand.REDMI_POCO_XIAOMI
            name.contains("iqoo") -> Brand.IQOO
            name.contains("vivo") || name.contains("oppo") -> Brand.VIVO_OPPO
            name.contains("oneplus") || name.contains("one plus") || name.contains("samsung") || name.contains("galaxy") ->
                Brand.ONEPLUS_SAMSUNG
            name.contains("infinix") || name.contains("realme") -> Brand.INFINIX_REALME
            else -> Brand.ONEPLUS_SAMSUNG
        }
    }

    fun calculate(brand: Brand, ram: Int, storageGb: Int): SensResult {
        val baseGeneral = when (brand) {
            Brand.REDMI_POCO_XIAOMI -> 180
            Brand.VIVO_OPPO -> 172
            Brand.IQOO -> 190
            Brand.ONEPLUS_SAMSUNG -> 160
            Brand.INFINIX_REALME -> 175
        }

        val baseDpi = when (brand) {
            Brand.REDMI_POCO_XIAOMI -> 456
            Brand.VIVO_OPPO -> 460
            Brand.IQOO -> 460
            Brand.ONEPLUS_SAMSUNG -> 460
            Brand.INFINIX_REALME -> 447
        }

        val generalRamAdj = when (brand) {
            Brand.REDMI_POCO_XIAOMI -> when (ram) {
                2 -> +12
                3 -> +8
                4 -> +4
                6 -> 0
                8 -> -4
                12 -> -12
                16 -> -18
                else -> 0
            }
            Brand.VIVO_OPPO -> when (ram) {
                2 -> +16
                3 -> +14
                4 -> +10
                6 -> 0
                8 -> -4
                12 -> -10
                16 -> -12
                else -> 0
            }
            Brand.IQOO -> when (ram) {
                2 -> +8
                3 -> +6
                4 -> -8
                6 -> -10
                8 -> -18
                12 -> -20
                16 -> -25
                else -> 0
            }
            Brand.ONEPLUS_SAMSUNG -> when (ram) {
                2 -> +26
                3 -> +24
                4 -> +20
                6 -> +15
                8 -> +8
                12 -> +5
                16 -> +4
                else -> 0
            }
            Brand.INFINIX_REALME -> when (ram) {
                2 -> +12
                3 -> +8
                4 -> +4
                6 -> +1
                8 -> -4
                12 -> -5
                16 -> -6
                else -> 0
            }
        }

        val dpiRamAdj = when (brand) {
            Brand.REDMI_POCO_XIAOMI -> when (ram) {
                2 -> +8
                3 -> +6
                4 -> +4
                6 -> +2
                8 -> -2
                12 -> -4
                16 -> -6
                else -> 0
            }
            else -> when (ram) {
                2 -> +10
                3 -> +9
                4 -> +8
                6 -> +6
                8 -> 0
                12 -> -2
                16 -> -8
                else -> 0
            }
        }

        val fireButton = when (brand) {
            Brand.REDMI_POCO_XIAOMI -> when (ram) {
                2 -> 44
                3 -> 43
                4 -> 46
                6 -> 48
                8 -> 48
                12 -> 50
                16 -> 52
                else -> 48
            }
            else -> when (ram) {
                2 -> 42
                3 -> 42
                4 -> 44
                6 -> 46
                8 -> 48
                12 -> 48
                16 -> 50
                else -> 46
            }
        }

        val storageAdj = if (storageGb < 128) +2 else -2

        val general = baseGeneral + generalRamAdj + storageAdj
        val dpi = baseDpi + dpiRamAdj + storageAdj

        return SensResult(
            general = general,
            redDot = general - 87,
            scope2x = general - 56,
            scope4x = general - 66,
            sniper = general - 82,
            dpi = dpi,
            fireButton = fireButton + storageAdj
        )
    }
}
