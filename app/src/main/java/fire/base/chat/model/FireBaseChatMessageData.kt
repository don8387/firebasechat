package fire.base.chat.model


import com.google.gson.annotations.SerializedName

data class FireBaseChatMessageData(
        @SerializedName("message")
        val message: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("profilecolor")
        val profilecolor: String
)