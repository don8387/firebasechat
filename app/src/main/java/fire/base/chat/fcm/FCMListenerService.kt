package fire.base.chat.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import fire.base.chat.AppChat
import fire.base.chat.BuildConfig
import fire.base.chat.model.FireBaseChatMessageData
import fire.base.chat.model.FireBaseChatMessage

class FCMListenerService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        BuildConfig.DEBUG.takeIf { it }?.also {
            logRemoteMessage(remoteMessage)
        }

        remoteMessage.data.takeIf { it.isNotEmpty() }?.let {
            FCMListenerServiceInteractor.passData(
                    FireBaseChatMessage(
                            FireBaseChatMessageData(
                                    it["message"]!! ?: "",
                                    it["name"]!! ?: "",
                                    it["profilecolor"]!! ?: ""
                            ), ""
                    )
            )
        }
    }

    private fun logRemoteMessage(remoteMessage: RemoteMessage) {
        Log.d(AppChat.TAG, "From: ${remoteMessage.from}")

        remoteMessage.data.takeIf { it.isNotEmpty() }?.let {
            Log.d("TAG", "Message data payload: " + remoteMessage.data)
        }
    }
}
