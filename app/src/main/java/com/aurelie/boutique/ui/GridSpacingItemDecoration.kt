package com.aurelie.boutique.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Even gutters for a 2-column staggered grid. [spacing] is the full gap between
 * columns (and rows) in pixels; each item gets half on the inner edges.
 */
class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val half = spacing / 2
        val lp = view.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        val spanIndex = lp?.spanIndex ?: (parent.getChildAdapterPosition(view) % spanCount)

        outRect.left = if (spanIndex == 0) 0 else half
        outRect.right = if (spanIndex == spanCount - 1) 0 else half
        outRect.bottom = spacing
    }
}
