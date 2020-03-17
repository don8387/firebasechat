package fire.base.chat.fcm

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FBChatFCMListenerService : FirebaseMessagingService() {

    companion object {
        private var PRIVATE_MODE = 0
        private val PREF_NAME = "fire-base-chat"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        token?.let { newToken: String ->
            updateToken(newToken)
        }
    }

    private fun updateToken(newToken: String) {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        sharedPref.edit {
            putString("FCMToken", newToken)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }
}
