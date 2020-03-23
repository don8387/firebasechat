package fire.base.chat.messenger

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fire.base.chat.R


class MessageAdapter(
        private var messages: ArrayList<MessageItem> = arrayListOf()
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(container.context)
                        .inflate(
                                R.layout.message_item,
                                container,
                                false
                        )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(newMessageList: List<MessageItem>) {
        messages.addAll(0, newMessageList)
        notifyItemRangeInserted(0, newMessageList.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val text = view.findViewById<TextView>(R.id.message_text)
        private val icon = view.findViewById<View>(R.id.profile_icon)
        private val name = view.findViewById<TextView>(R.id.user_name)

        fun bind(item: MessageItem) {
            text.text = item.text
            name.text = item.name

            changeIconColor(item.profilecolor)
        }

        private fun changeIconColor(profileColor: String) {
            try {
                profileColor
                        .takeIf { !it.isBlank() }
                        ?.let { hexColorCode: String ->
                            Color.parseColor(hexColorCode)
                        }
                        ?.also { colorCode: Int ->
                            when (val background: Drawable = icon.background) {
                                is ShapeDrawable -> {
                                    background.paint.color = colorCode
                                }
                                is GradientDrawable -> {
                                    background.setColor(colorCode)
                                }
                                is ColorDrawable -> {
                                    background.color = colorCode
                                }
                            }
                        }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getLastPosition() = if (messages.lastIndex == -1) 0 else messages.lastIndex
}

