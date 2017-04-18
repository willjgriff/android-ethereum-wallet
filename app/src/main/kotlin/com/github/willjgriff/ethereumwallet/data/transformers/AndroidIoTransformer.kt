package com.github.willjgriff.ethereumwallet.data.transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Will on 16/03/2017.
 */
class AndroidIoTransformer<TYPE> : ObservableTransformer<TYPE, TYPE> {

    override fun apply(observable: Observable<TYPE>): ObservableSource<TYPE> {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}