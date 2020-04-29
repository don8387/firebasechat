package fire.base.chat.presentation.messenger

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fire.base.chat.AppChat
import fire.base.chat.FireBaseChatConstants.TOPIC_FOR_REQUEST
import fire.base.chat.bot.TickBot
import fire.base.chat.data.MessageRepositoryRemote
import fire.base.chat.fcm.FCMListenerServiceInteractor
import fire.base.chat.model.FireBaseChatMessage
import fire.base.chat.presentation.messenger.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MessengerViewModel(
    private val messageRepositoryRemote: MessageRepositoryRemote,
    private val fcmListenerServiceInteractor: FCMListenerServiceInteractor = FCMListenerServiceInteractor
) : BaseViewModel(), TickBot.TickActionListener {

    private val lastReceivedMessage = MutableLiveData<MessageItem>()
    private val botList = arrayListOf<TickBot>()

    init {
        fcmListenerServiceInteractor
            .getSource()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it?.let { fireBaseChatMessage: FireBaseChatMessage ->
                    lastReceivedMessage.value =
                        MessageItem(
                            text = fireBaseChatMessage.fireBaseChatMessageData.message,
                            name = fireBaseChatMessage.fireBaseChatMessageData.name,
                            profilecolor = fireBaseChatMessage.fireBaseChatMessageData.profilecolor

                        )
                }
            }
    }

    fun getLastReceivedMessage(): LiveData<MessageItem> = lastReceivedMessage

    @SuppressLint("CheckResult")
    fun sendMessage(
        topic: String = TOPIC_FOR_REQUEST,
        name: String = "you",
        message: String = "",
        profileColor: String = "#BFD81B60"
    ) {
        messageRepositoryRemote.putNewMessage(
            topic = topic,
            name = name,
            profileColor = profileColor,
            message = message
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(AppChat.TAG, it.body() ?: "")
            }, {
                it.printStackTrace()
            })
    }

    fun addBots(botCount: Int) {
        repeat(botCount) {
            botList.add(TickBot(this))
        }
    }

    override fun onTickDo(botMessage: TickBot.TickBotPhrase) {
        sendMessage(
            name = botMessage.name,
            message = botMessage.message,
            topic = botMessage.topic,
            profileColor = botMessage.iconColor
        )
    }
}
