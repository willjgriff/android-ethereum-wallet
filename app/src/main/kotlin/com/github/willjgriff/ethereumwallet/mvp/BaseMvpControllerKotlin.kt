package com.github.willjgriff.ethereumwallet.mvp

import android.view.View
import com.bluelinelabs.conductor.rxlifecycle2.RxController

/**
 * Created by williamgriffiths on 03/05/2017.
 */
abstract class BaseMvpControllerKotlin<VIEW, out PRESENTER : MvpPresenter<VIEW>> : RxController() {

    protected abstract val mvpView: VIEW

    protected abstract val presenter: PRESENTER

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.bindView(mvpView)
        presenter.viewReady()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.unbindView()
    }
}