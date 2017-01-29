package com.github.willjgriff.ethereumwallet.mvp;

/**
 * Created by Will on 15/08/2016.
 */

public interface MvpPresenter<VIEW> {

	void bindView(VIEW mvpView);

	void unbindView();
}
