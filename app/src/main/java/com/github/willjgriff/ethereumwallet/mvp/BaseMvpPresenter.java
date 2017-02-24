package com.github.willjgriff.ethereumwallet.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Will on 09/11/2016.
 */

public abstract class BaseMvpPresenter<VIEW> implements MvpPresenter<VIEW> {

	private VIEW mMvpView;
	private CompositeDisposable mCompositeDisposable;

	public BaseMvpPresenter() {
		mCompositeDisposable = new CompositeDisposable();
	}

	@Override
	public final void bindView(VIEW mvpView) {
		mMvpView = mvpView;
	}

	@Override
	public final void unbindView() {
		mCompositeDisposable.clear();
		releaseViewReferences();
		mMvpView = null;
	}

	/**
	 * If we wish to retain this Presenter beyond the life of the Controller or View that
	 * it is associated too, override this method and set to null any View references /
	 * runtime dependencies that can no longer be used to prevent potential memory leaks.
	 */
	public void releaseViewReferences() {

	};

	public void addDisposable(Disposable disposable) {
		mCompositeDisposable.add(disposable);
	}

	public VIEW getView() {
		return mMvpView;
	}
}
