package fire.base.chat.messenger.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(
        protected val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread(),
        protected val workThreadScheduler: Scheduler = Schedulers.io()
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    protected fun <T> Observable<T>.addSubscription(
            onNext: ((T) -> Unit)? = null,
            onError: ((Throwable) -> Unit)? = null,
            onComplete: (() -> Unit)? = null
    ) {
        this.subscribeOn(workThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ onNext?.invoke(it) }, { onError?.invoke(it) }, { onComplete?.invoke() })
                .let(compositeDisposable::add)
    }

    protected fun <T> Single<T>.addSubscription(
            onNext: ((T) -> Unit)? = null,
            onError: ((Throwable) -> Unit)? = null
    ) {
        this.subscribeOn(workThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ onNext?.invoke(it) }, { onError?.invoke(it) })
                .let(compositeDisposable::add)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun clearDisposable() {
        compositeDisposable.clear()
    }
}
