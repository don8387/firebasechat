package fire.base.chat.api

import fire.base.chat.api.base.BaseApiCreator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent

class FireBaseChatApiCreator() : BaseApiCreator(), KoinComponent {

    override fun createHttpClient(interceptor: Interceptor): OkHttpClient {
        return getDefaultHttpClientBuilder(interceptor)
                .build()
    }
}