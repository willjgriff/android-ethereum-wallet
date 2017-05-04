package com.github.willjgriff.ethereumwallet.mvp

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface MvpPresenter<VIEW> {

    fun bindView(mvpView: VIEW)

    fun unbindView()

    /**
     * When implementing this method, asynchronous operations should be converted to observables
     * and their disposables should be added with [BaseMvpPresenterKotlin.addDisposable].
     * This will ensure callbacks are only executed between [BaseMvpPresenterKotlin.bindView]
     * and [BaseMvpPresenterKotlin.unbindView], preventing View access when it isn't available.
     */
    fun viewReady()
}