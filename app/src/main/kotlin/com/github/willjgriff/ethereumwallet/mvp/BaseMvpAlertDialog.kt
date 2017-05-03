package com.github.willjgriff.ethereumwallet.mvp

import android.content.Context
import android.support.v7.app.AlertDialog

/**
 * Created by williamgriffiths on 03/05/2017.
 */
abstract class BaseMvpAlertDialog<VIEW, out PRESENTER : MvpPresenter<VIEW>>(context: Context) : AlertDialog(context) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(mvpView)
        presenter.viewReady()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView()
    }

    protected abstract val mvpView: VIEW

    protected abstract val presenter: PRESENTER
}