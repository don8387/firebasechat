package fire.base.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import fire.base.chat.presentation.messenger.MessengerFragment

class MessengerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messenger_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MessengerFragment()
                )
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()

//        subscribeToTopic(FireBaseChatConstants.TOPIC_FOR_SUBSCRIPTION)
    }

    private fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                val message = if (task.isSuccessful) {
                    getString(R.string.success_subscription)
                } else {
                    getString(R.string.failed_subscription)
                }

                Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStop() {
        super.onStop()

//        unsubscribeFromTopic(FireBaseChatConstants.TOPIC_FOR_SUBSCRIPTION)
    }

    private fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                val message = if (task.isSuccessful) {
                    getString(R.string.success_subscription)
                } else {
                    getString(R.string.failed_subscription)
                }

                Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
            }
    }
}
