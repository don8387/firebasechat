package fire.base.chat.api.base

import okhttp3.Interceptor

abstract class BaseApiInterceptor : Interceptor {

    companion object {
        const val MULTIPLE_CHOICES_HTTP_CODE = 300
        const val BAD_REQUEST_HTTP_CODE = 400

        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_JSON = "application/json"
    }
}
