package com.github.willjgriff.ethereumwallet.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.androidIoSchedule(): Observable<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.replayConnect(numberOfItems: Int): Observable<T> =
        replay(numberOfItems).autoConnect()