package com.github.wiljgriff.ethereumwallet.data.transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Will on 16/03/2017.
 */
class AndroidIoTransformer<TYPE> : ObservableTransformer<TYPE, TYPE> {

    override fun apply(observable: Observable<TYPE>?): ObservableSource<TYPE> {
        return observable
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?: ObservableSource { }
    }
}