package fire.base.chat.data

import fire.base.chat.api.FireBaseChatApi
import fire.base.chat.api.FireBaseChatApiFactory
import fire.base.chat.model.FireBaseChatMessage
import fire.base.chat.model.FireBaseChatMessageData
import io.reactivex.Single
import org.koin.core.KoinComponent
import retrofit2.Response

class MessageRepositoryRemote(private val fireBaseChatApiFactory: FireBaseChatApiFactory) : MessageRepository, KoinComponent {

    override fun putNewMessage(topic: String, name: String, message: String, profileColor: String): Single<Response<String>> {
        return fireBaseChatApiFactory.api.flatMap { chatApi: FireBaseChatApi ->
            chatApi.sendMessage(
                    FireBaseChatMessage(
                            to = topic,
                            fireBaseChatMessageData = FireBaseChatMessageData(
                                    message = message,
                                    profilecolor = profileColor,
                                    name = name
                            )
                    )
            )
        }
    }
}