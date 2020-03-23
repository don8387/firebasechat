package fire.base.chat.api

import fire.base.chat.api.base.BaseApiInterceptor
import fire.base.chat.api.exception.BadRequestHttpException
import okhttp3.Interceptor
import okhttp3.Response

open class FireBaseChatInterceptor : BaseApiInterceptor() {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()
            val response: Response = chain.proceed(
                    request.newBuilder()
                            .addHeader("Authorization", "key=AAAAzHLgEb0:APA91bF189gja2twKdPU-dvIZMpiduN4-dIOJNtuvQckZfIvj_HHlSxnDPhyRZsAXhZP_Nc1SL603hUpdYHGZPThdLU4cnShJK2ggprHkAjeUDwaomK_xxfLhbnmtm3PH52Yc8wTCN2D")
                            .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                            .build()
            )

            when (response.code) {
                BAD_REQUEST_HTTP_CODE -> throw BadRequestHttpException()
                else -> return response
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw BadRequestHttpException()
        }
    }
}
