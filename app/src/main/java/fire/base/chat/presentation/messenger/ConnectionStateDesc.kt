package fire.base.chat.presentation.messenger

import fire.base.chat.R

enum class ConnectionStateDesc(public val drawableId: Int, public val stringId: Int) {
    CONNECTED(
        R.drawable.connection_active_bg,
        R.string.connection_active_desc
    ),
    DISCONNECTED(
        R.drawable.connection_inactive_bg,
        R.string.connection_inactive_desc
    )
}