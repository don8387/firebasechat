package fire.base.chat.fcm

import com.google.gson.Gson
import fire.base.chat.model.FireBaseChatMessage
import io.reactivex.subjects.PublishSubject


object FCMListenerServiceInteractor {

    private val source: PublishSubject<FireBaseChatMessage> = PublishSubject.create()

    public fun getSource(): PublishSubject<FireBaseChatMessage> {
        return source
    }

    public fun passData(fireBaseChatMessage: FireBaseChatMessage) {
        source.onNext(fireBaseChatMessage)
    }

    public fun complete() {
        source.onComplete()
    }

    public fun createFireBaseChatMessage(json: String): FireBaseChatMessage {
        return Gson().fromJson(json, FireBaseChatMessage::class.java)
    }
}