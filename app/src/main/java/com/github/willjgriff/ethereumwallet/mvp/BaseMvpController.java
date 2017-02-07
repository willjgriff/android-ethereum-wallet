package com.github.willjgriff.ethereumwallet.mvp;

import android.support.annotation.NonNull;
import android.view.View;

import com.bluelinelabs.conductor.rxlifecycle2.RxController;

/**
 * Created by Will on 12/11/2016.
 */

public abstract class BaseMvpController<VIEW, PRESENTER extends MvpPresenter<VIEW>> extends RxController {

	@Override
	protected void onAttach(@NonNull View view) {
		super.onAttach(view);
		getPresenter().bindView(getMvpView());
	}

	@Override
	protected void onDetach(@NonNull View view) {
		super.onDetach(view);
		getPresenter().unbindView();
	}

	protected abstract VIEW getMvpView();

	protected abstract PRESENTER getPresenter();
}
