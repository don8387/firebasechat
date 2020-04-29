package fire.base.chat.presentation.messenger

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MessageItemDecorator(
    private val topMargin: Int = 8,
    private val bottomMargin: Int = 8,
    private val leftMargin: Int = 24,
    private val rightMargin: Int = 24
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == parent.childCount - 1) {
                top = topMargin
            }
            left = leftMargin
            right = rightMargin
            bottom = bottomMargin
        }
    }
}