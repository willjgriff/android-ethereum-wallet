package com.github.willjgriff.ethereumwallet.mvp.listmvp;

import java.util.List;

/**
 * Created by Will on 12/11/2016.
 */
public interface ListMvpView<TYPE> {

	void satDataList(List<TYPE> newsList);

	void showLoadingView();

	void hideLoadingView();

	void showError(Throwable throwable);
}
