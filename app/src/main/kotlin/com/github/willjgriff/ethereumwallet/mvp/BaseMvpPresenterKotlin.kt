package com.github.willjgriff.ethereumwallet.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by williamgriffiths on 03/05/2017.
 */
abstract class BaseMvpPresenterKotlin<VIEW> : MvpPresenter<VIEW> {

    var view: VIEW? = null
        private set
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun bindView(mvpView: VIEW) {
        view = mvpView
    }

    override fun unbindView() {
        compositeDisposable.clear()
        releaseViewReferences()
        /**
         * This is only necessary if we retain the Presenter beyond the life of
         * the View, we will do it anyway in case there's something I've
         * overlooked or we wish to retain the Presenters in the future
         */
        view = null
    }

    /**
     * If we wish to retain this Presenter beyond the life of the Controller or View that
     * it is associated too, override this method and set to null any View references /
     * runtime dependencies that can no longer be used to prevent potential memory leaks.
     */
    open fun releaseViewReferences() {

    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}