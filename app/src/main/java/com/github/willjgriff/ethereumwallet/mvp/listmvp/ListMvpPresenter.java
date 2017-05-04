package com.github.willjgriff.ethereumwallet.mvp.listmvp;

import com.github.willjgriff.ethereumwallet.data.RefreshableRepository;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin;

import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Will on 12/11/2016.
 */
public abstract class ListMvpPresenter<TYPE, VIEW extends ListMvpView<TYPE>, QUERY>
	extends BaseMvpPresenterKotlin<VIEW> {

	public void setRefreshTrigger(Observable<Object> swipeRefreshObservable) {
		getRepository().setRefreshTrigger(swipeRefreshObservable);
	}

	protected abstract RefreshableRepository getRepository();

	@Override
	public void viewReady() {
		getView().showLoadingView();
		addDisposable(getDataObservable().subscribe(dataList -> {
			dataLoaded(dataList);
			getView().satDataList(dataList);
			getView().hideLoadingView();
		}, throwable -> {
			Timber.e(throwable, "Error fetching data");
			getView().showError(throwable);
		}));
	}

	protected abstract Observable<List<TYPE>> getDataObservable();

	/**
	 * Override if we want to do more than just set the data on the list once it's loaded.
	 */
	protected void dataLoaded(List<TYPE> dataList) {
	}

}
