package fire.base.chat.model


import com.google.gson.annotations.SerializedName

data class FireBaseChatMessage(
        @SerializedName("data")
        val fireBaseChatMessageData: FireBaseChatMessageData,
        @SerializedName("to")
        val to: String
)