package fire.base.chat.data

import io.reactivex.Single
import retrofit2.Response

interface MessageRepository {

    fun putNewMessage(
            topic: String,
            name: String,
            message: String,
            profileColor: String
    ): Single<Response<String>>

}