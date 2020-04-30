package fire.base.chat

import android.app.Application
import fire.base.chat.api.FireBaseChatApiCreator
import fire.base.chat.api.FireBaseChatApiFactory
import fire.base.chat.data.MessageRepositoryRemote
import fire.base.chat.presentation.messenger.MessengerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppChat : Application() {
    companion object {
        public const val TAG = "AppChatTAG"
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single { FireBaseChatApiCreator() }
            single {
                FireBaseChatApiFactory(fireBaseChatApiCreator = get())
            }
            single {
                MessageRepositoryRemote(fireBaseChatApiFactory = get())
            }
            viewModel {
                MessengerViewModel(messageRepositoryRemote = get())
            }
        }

        startKoin {
            androidLogger()
            androidContext(this@AppChat)
            modules(appModule)
        }
    }
}