package fire.base.chat.api

import fire.base.chat.model.FireBaseChatMessage
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FireBaseChatApi {

    @POST("fcm/send")
    fun sendMessage(@Body user: FireBaseChatMessage): Single<Response<String>>
}

