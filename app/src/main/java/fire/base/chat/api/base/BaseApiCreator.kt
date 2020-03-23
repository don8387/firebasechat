package fire.base.chat.api.base


import fire.base.chat.BuildConfig
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseApiCreator : KoinComponent {

    companion object {
        private const val BASE_URL = "https://fcm.googleapis.com/"
    }

    private val ADAPTER_FACTORY = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    private val REQUEST_TIMEOUT = TimeUnit.SECONDS.toMillis(30)

    abstract fun createHttpClient(interceptor: Interceptor): OkHttpClient

    inline fun <reified T> createApi(interceptor: Interceptor): Single<T> {
        return Observable.just(buildRetrofitInstance(createHttpClient(interceptor)))
                .map { retrofit -> retrofit.create(T::class.java) }
                .replay(1)
                .refCount()
                .firstOrError()
    }

    fun buildRetrofitInstance(
            client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(ADAPTER_FACTORY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getDefaultHttpClientBuilder(interceptor: Interceptor) = OkHttpClient.Builder().apply {
        connectTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
        writeTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
        addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
        }
    }
}