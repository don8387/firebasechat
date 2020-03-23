package fire.base.chat.bot

import fire.base.chat.FireBaseChatConstants.TOPIC_FOR_REQUEST
import fire.base.chat.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random


class TickBot(private val tickAction: TickActionListener) {
    private val lastTick: AtomicLong = AtomicLong(0L)
    private var subscription: Disposable? = null

    private lateinit var name: String
    private lateinit var iconColor: String
    private lateinit var topic: String

    init {
        initBotSelf()

        start()
    }

    private fun initBotSelf() {
        with(Util.generateHexColorCode()) {
            name = this
            iconColor = this
        }

        topic = TOPIC_FOR_REQUEST
    }

    fun start() {
        subscription = Observable.interval(getInterval(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { tick -> lastTick.getAndIncrement() }
                .subscribe({
                    tickAction.onTickDo(
                            TickBotPhrase(
                                    name = name,
                                    iconColor = iconColor,
                                    message = generateBotPhrase(),
                                    topic = topic
                            )
                    )
                }, {
                    it.printStackTrace()
                })

    }

    private fun getInterval(): Long = Random.nextLong(2, 10)

    private fun generateBotPhrase(): String {
        return StringBuilder("My fav colors are ").let { phrase: StringBuilder ->
            repeat(Random.nextInt(1, 5)) {
                phrase.append("${Util.generateHexColorCode()} ")
            }
            phrase.toString()
        }
    }

    fun stop() {
        subscription?.let {
            it.takeIf { !it.isDisposed }?.dispose()
        }
    }

    interface TickActionListener {
        fun onTickDo(botMessage: TickBotPhrase)
    }

    data class TickBotPhrase(
            val name: String,
            val iconColor: String,
            val message: String,
            val topic: String
    )
}