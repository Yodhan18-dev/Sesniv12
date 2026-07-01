package com.bdkjupiter.sensi.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.bdkjupiter.sensi.R

class SoundManager(private val context: Context) {

    private val soundPool: SoundPool by lazy {
        SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
    }

    // Sound IDs - loaded from raw resources
    private val soundYes by lazy { soundPool.load(context, R.raw.sound_yes, 1) }
    private val soundNo by lazy { soundPool.load(context, R.raw.sound_no, 1) }
    private val soundGenerate by lazy { soundPool.load(context, R.raw.sound_generate, 1) }
    private val soundClick by lazy { soundPool.load(context, R.raw.sound_click, 1) }

    fun playYes() = playSound(soundYes)
    fun playNo() = playSound(soundNo)
    fun playGenerate() = playSound(soundGenerate)
    fun playClick() = playSound(soundClick)

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}
