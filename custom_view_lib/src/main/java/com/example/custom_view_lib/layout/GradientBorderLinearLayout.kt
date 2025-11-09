package com.example.custom_view_lib.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.custom_view_lib.R


class GradientBorderLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var borderStartColor: Int = Color.TRANSPARENT
    private var borderEndColor: Int = Color.TRANSPARENT
    private var bgStartColor: Int = Color.TRANSPARENT
    private var bgEndColor: Int = Color.TRANSPARENT
    private var borderWidth: Float = 0f
    private var cornerRadius: Float = 0f
    private var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT

    init {
        context.withStyledAttributes(attrs, R.styleable.GradientBorderLinearLayout) {
            borderStartColor =
                getColor(R.styleable.GradientBorderLinearLayout_borderStartColor, Color.TRANSPARENT)
            borderEndColor =
                getColor(R.styleable.GradientBorderLinearLayout_borderEndColor, borderStartColor)
            bgStartColor =
                getColor(R.styleable.GradientBorderLinearLayout_bgStartColor, Color.TRANSPARENT)
            bgEndColor = getColor(R.styleable.GradientBorderLinearLayout_bgEndColor, bgStartColor)
            borderWidth = getDimension(R.styleable.GradientBorderLinearLayout_borderWidth, 0f)
            cornerRadius = getDimension(R.styleable.GradientBorderLinearLayout_cornerRadius, 0f)

            val orientationValue =
                getInt(R.styleable.GradientBorderLinearLayout_gradientOrientation, 0)
            orientation = when (orientationValue) {
                1 -> GradientDrawable.Orientation.TOP_BOTTOM
                2 -> GradientDrawable.Orientation.BL_TR
                3 -> GradientDrawable.Orientation.TL_BR
                else -> GradientDrawable.Orientation.LEFT_RIGHT
            }
        }

        background = GradientBorderDrawable(
            borderStartColor,
            borderEndColor,
            bgStartColor,
            bgEndColor,
            borderWidth,
            cornerRadius,
            orientation
        )
    }
}

/**
 * 专门用于绘制“中空渐变边框 + 渐变背景”的 Drawable
 */
class GradientBorderDrawable(
    private var borderStartColor: Int,
    private var borderEndColor: Int,
    private var bgStartColor: Int,
    private var bgEndColor: Int,
    private var borderWidth: Float,
    private var cornerRadius: Float,
    private var orientation: GradientDrawable.Orientation
) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    override fun draw(canvas: Canvas) {
        val w = bounds.width().toFloat()
        val h = bounds.height().toFloat()
        if (w <= 0f || h <= 0f) return

        // 1. 绘制背景渐变
        val bgShader = createLinearShader(0f, 0f, w, h, bgStartColor, bgEndColor)
        paint.shader = bgShader
        paint.style = Paint.Style.FILL

        val inset = if (borderWidth > 0f) borderWidth else 0f
        val innerLeft = inset
        val innerTop = inset
        val innerRight = w - inset
        val innerBottom = h - inset
        val innerRadius = (cornerRadius - inset).coerceAtLeast(0f)

        // 绘制背景（内矩形）
        if (innerLeft < innerRight && innerTop < innerBottom) {
            canvas.drawRoundRect(
                RectF(innerLeft, innerTop, innerRight, innerBottom),
                innerRadius, innerRadius, paint
            )
        }

        // 2. 绘制边框（使用Path创建边框环）
        if (borderWidth > 0f) {
            path.reset()

            // 外圆角矩形
            val outerRect = RectF(0f, 0f, w, h)

            // 内圆角矩形
            val innerRect = RectF(innerLeft, innerTop, innerRight, innerBottom)

            // 使用Path创建边框环：外矩形 - 内矩形
            path.addRoundRect(outerRect, cornerRadius, cornerRadius, Path.Direction.CW)
            path.addRoundRect(innerRect, innerRadius, innerRadius, Path.Direction.CCW)
            path.fillType = Path.FillType.EVEN_ODD

            // 绘制边框渐变
            val borderShader = createLinearShader(0f, 0f, w, h, borderStartColor, borderEndColor)
            paint.shader = borderShader
            canvas.drawPath(path, paint)
        }
    }

    private fun createLinearShader(
        x0: Float, y0: Float, x1: Float, y1: Float,
        startColor: Int, endColor: Int
    ): Shader {
        return when (orientation) {
            GradientDrawable.Orientation.TOP_BOTTOM ->
                LinearGradient(x0, y0, x0, y1, startColor, endColor, Shader.TileMode.CLAMP)
            GradientDrawable.Orientation.BL_TR ->
                LinearGradient(x0, y1, x1, y0, startColor, endColor, Shader.TileMode.CLAMP)
            GradientDrawable.Orientation.TL_BR ->
                LinearGradient(x0, y0, x1, y1, startColor, endColor, Shader.TileMode.CLAMP)
            else ->
                LinearGradient(x0, y0, x1, y0, startColor, endColor, Shader.TileMode.CLAMP)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}

