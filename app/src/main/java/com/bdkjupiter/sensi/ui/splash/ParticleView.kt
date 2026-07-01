package com.bdkjupiter.sensi.ui.splash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    data class Particle(
        var x: Float, var y: Float,
        var vx: Float, var vy: Float,
        var radius: Float, var alpha: Int,
        var color: Int
    )

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var running = false
    private val neonColors = intArrayOf(
        Color.parseColor("#00FFFF"),
        Color.parseColor("#0080FF"),
        Color.parseColor("#00FF88"),
        Color.parseColor("#FF0080")
    )

    fun startParticles() {
        running = true
        postDelayed(::spawnAndDraw, 16)
    }

    private fun spawnAndDraw() {
        if (!running) return
        // Spawn new
        if (particles.size < 60 && width > 0) {
            repeat(3) {
                val angle = Random.nextFloat() * 2 * Math.PI.toFloat()
                val speed = Random.nextFloat() * 2f + 0.5f
                particles.add(
                    Particle(
                        x = Random.nextFloat() * width,
                        y = Random.nextFloat() * height,
                        vx = cos(angle) * speed,
                        vy = sin(angle) * speed,
                        radius = Random.nextFloat() * 4f + 1f,
                        alpha = (Random.nextFloat() * 200 + 55).toInt(),
                        color = neonColors[Random.nextInt(neonColors.size)]
                    )
                )
            }
        }
        // Update positions
        val iter = particles.iterator()
        while (iter.hasNext()) {
            val p = iter.next()
            p.x += p.vx
            p.y += p.vy
            p.alpha -= 2
            if (p.alpha <= 0 || p.x < 0 || p.x > width || p.y < 0 || p.y > height) {
                iter.remove()
            }
        }
        invalidate()
        postDelayed(::spawnAndDraw, 16)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (p in particles) {
            paint.color = p.color
            paint.alpha = p.alpha
            canvas.drawCircle(p.x, p.y, p.radius, paint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        running = false
    }
}
