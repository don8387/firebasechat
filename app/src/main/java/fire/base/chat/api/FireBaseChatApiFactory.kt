package fire.base.chat.api

import org.koin.core.KoinComponent

class FireBaseChatApiFactory(fireBaseChatApiCreator: FireBaseChatApiCreator) : KoinComponent {

    val api = fireBaseChatApiCreator.createApi<FireBaseChatApi>(FireBaseChatInterceptor())

}