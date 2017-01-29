package com.github.willjgriff.ethereumwallet.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;

/**
 * Created by Will on 12/11/2016.
 */

public abstract class BaseMvpController<VIEW, PRESENTER extends MvpPresenter<VIEW>> extends RxController {

	private PRESENTER mPresenter;

//	public BaseMvpController(PRESENTER presenter) {
//		mPresenter = presenter;
//	}

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

	protected PRESENTER getPresenter() {
		if (mPresenter == null) {
			mPresenter = createPresenter();
		}
		return mPresenter;
	}

	protected abstract VIEW getMvpView();

	protected abstract PRESENTER createPresenter();
}
