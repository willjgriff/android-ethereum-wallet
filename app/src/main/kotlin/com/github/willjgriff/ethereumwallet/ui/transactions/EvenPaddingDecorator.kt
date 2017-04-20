package com.github.willjgriff.ethereumwallet.ui.transactions

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.willjgriff.ethereumwallet.ui.utils.UiUtils

/**
 * Created by williamgriffiths on 20/04/2017.
 */
class EvenPaddingDecorator(private val topBottomPadding: Int,
                           private val leftRightPadding: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val topBottomPixelPadding = UiUtils.convertDpToPixel(topBottomPadding, view.getContext())
        val leftRightPixelPadding = UiUtils.convertDpToPixel(leftRightPadding, view.getContext())
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