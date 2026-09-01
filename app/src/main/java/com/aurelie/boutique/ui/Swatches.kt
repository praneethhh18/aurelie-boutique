package com.aurelie.boutique.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import com.google.android.material.color.MaterialColors

/** Shared builders so the grid card and the detail screen render identical colour dots. */
object Swatches {

    fun dp(context: Context, value: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics
    ).toInt()

    private fun hairline(context: Context): Int =
        MaterialColors.getColor(context, com.google.android.material.R.attr.colorOutline, Color.LTGRAY)

    private fun accent(context: Context): Int =
        MaterialColors.getColor(context, com.google.android.material.R.attr.colorPrimary, Color.DKGRAY)

    /** A plain filled dot with a hairline edge. */
    fun dot(context: Context, color: Int): Drawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(color)
        setStroke(dp(context, 1), hairline(context))
    }

    /** A dot that grows an accent ring when [selected]. */
    fun selectableDot(context: Context, color: Int, selected: Boolean): Drawable {
        val fill = dot(context, color)
        if (!selected) return fill
        val ring = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.TRANSPARENT)
            setStroke(dp(context, 2), accent(context))
        }
        val layer = LayerDrawable(arrayOf(ring, fill))
        val inset = dp(context, 4)
        layer.setLayerInset(1, inset, inset, inset, inset)
        return layer
    }
}
