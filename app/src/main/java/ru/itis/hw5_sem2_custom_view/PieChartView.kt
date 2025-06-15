package ru.itis.hw5_sem2_custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt

class PieChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    var sectorCount: Int = 5
        set(value) {
            field = value
            invalidate()
        }

    var sectorColors: List<Int> = listOf(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA)
        set(value) {
            field = value
            invalidate()
        }

    private var activeSector: Int? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = min(width, height)
        val strokeWidth = size * 0.3f
        val radius = size / 2f - strokeWidth / 2f
        val centerX = width / 2f
        val centerY = height / 2f
        val sectorAngle = 360f / sectorCount

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.strokeCap = Paint.Cap.ROUND

        setLayerType(LAYER_TYPE_SOFTWARE, paint)
        paint.setShadowLayer(8f, 4f, 4f, Color.argb(100, 0, 0, 0))

        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        for (i in 0 until sectorCount) {
            paint.color = if (i == activeSector) lightenColor(sectorColors[i]) else sectorColors[i]
            val startAngle = -180f + i * sectorAngle
            canvas.drawArc(rectF, startAngle, sectorAngle, false, paint)
        }

        paint.clearShadowLayer()

        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = size * 0.2f
        paint.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
        canvas.drawText(sectorCount.toString(), centerX, centerY + paint.textSize / 3, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val sector = getTouchedSector(event.x, event.y)
                if (sector != activeSector) {
                    activeSector = sector
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (getTouchedSector(event.x, event.y) == null) {
                    if (activeSector != null) {
                        activeSector = null
                        invalidate()
                    }
                }
            }
        }
        return true
    }

    private fun getTouchedSector(x: Float, y: Float): Int? {
        val centerX = width / 2f
        val centerY = height / 2f
        val dx = x - centerX
        val dy = y - centerY
        val distance = sqrt(dx * dx + dy * dy)

        val size = min(width, height)
        val strokeWidth = size * 0.18f
        val radius = size / 2f - strokeWidth / 2f

        val tolerance = strokeWidth * 0.75f
        val innerRadius = radius - tolerance
        val outerRadius = radius + tolerance

        if (distance < innerRadius || distance > outerRadius) return null

        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble()))
        angle = (angle + 360 + 180) % 360

        val sectorAngle = 360.0 / sectorCount
        return (angle / sectorAngle).toInt()
    }

    private fun lightenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = min(1f, hsv[2] * 1.4f)
        hsv[1] = maxOf(0f, hsv[1] * 0.7f)
        return Color.HSVToColor(hsv)
    }
}
