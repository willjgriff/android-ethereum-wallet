package com.github.willjgriff.ethereumwallet.mvp;

import android.support.annotation.NonNull;
import android.view.View;

import com.bluelinelabs.conductor.rxlifecycle2.RxController;

/**
 * Created by Will on 12/11/2016.
 */

public abstract class BaseMvpController<VIEW, PRESENTER extends MvpPresenter<VIEW>> extends RxController {

	private PRESENTER mPresenter;

	@Override
	protected void onAttach(@NonNull View view) {
		super.onAttach(view);
		getPresenter().bindView(getMvpView());
		getPresenter().viewReady();
	}

	@Override
	protected void onDetach(@NonNull View view) {
		super.onDetach(view);
		getPresenter().unbindView();
	}

	// TODO: Not sure whether I should use AutoFactory or Cache the Presenters in Components.
	public PRESENTER getPresenter() {
		if (mPresenter == null) {
			mPresenter = createPresenter();
		}
		return mPresenter;
	}

	protected abstract VIEW getMvpView();

	protected abstract PRESENTER createPresenter();
}
