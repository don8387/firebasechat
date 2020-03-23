package fire.base.chat

import android.content.Context
import android.net.ConnectivityManager
import kotlin.random.Random

object Util {
    public val allowedHexSymbols = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    public fun isInternetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    public fun generateHexColorCode(): String {
        fun getRandomHexSymbol(): String {
            return allowedHexSymbols[Random.nextInt(0, allowedHexSymbols.size - 1)]
        }

        return StringBuilder("#").let { builder: StringBuilder ->
            repeat(6) {
                builder.append(getRandomHexSymbol())
            }
            builder.toString()
        }
    }
}