package com.github.willjgriff.ethereumwallet.ui.utils.listdecorator

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.willjgriff.ethereumwallet.ui.utils.convertDpToPixel

/**
 * Created by williamgriffiths on 20/04/2017.
 */
class EvenPaddingDecorator(private val topBottomPadding: Int,
                           private val leftRightPadding: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val topBottomPixelPadding = topBottomPadding.convertDpToPixel(view.context)
        val leftRightPixelPadding = leftRightPadding.convertDpToPixel(view.context)
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            outRect.top = topBottomPixelPadding
        }

        outRect.apply {
            bottom = topBottomPixelPadding
            left = leftRightPixelPadding
            right = leftRightPixelPadding
        }

    }
}