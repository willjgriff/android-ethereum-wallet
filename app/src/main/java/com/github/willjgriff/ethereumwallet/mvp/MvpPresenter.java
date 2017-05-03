package com.github.willjgriff.ethereumwallet.mvp;

import io.reactivex.disposables.Disposable;

/**
 * Created by Will on 15/08/2016.
 */

public interface MvpPresenter<VIEW> {

	void bindView(VIEW mvpView);

	void unbindView();

	/**
	 * When implementing this method, asynchronous operations should be converted to observables
	 * and their disposables should be added with {@link BaseMvpPresenterKotlin#addDisposable(Disposable)}.
	 * This will ensure callbacks are only executed between {@link BaseMvpPresenterKotlin#bindView(Object)}
	 * and {@link BaseMvpPresenterKotlin#unbindView()}, preventing View access when it isn't available.
	 */
	void viewReady();
}
