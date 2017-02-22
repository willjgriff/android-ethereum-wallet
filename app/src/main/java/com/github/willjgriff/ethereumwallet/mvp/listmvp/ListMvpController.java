package com.github.willjgriff.ethereumwallet.mvp.listmvp;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.data.utils.ConnectivityUtils;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.mvp.listmvp.ListMvpViewHolder.ListItemListener;
import com.github.willjgriff.ethereumwallet.ui.utils.ErrorDisplayer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Will on 28/09/2016.
 * <p>
 * TODO: Sort out the static accesses.
 */

public abstract class ListMvpController<TYPE, VIEW extends ListMvpView<TYPE>, PRESENTER extends ListMvpPresenter<TYPE, VIEW, QUERY>, VIEWHOLDER extends ListMvpViewHolder<TYPE>, QUERY>
	extends BaseMvpController<VIEW, PRESENTER>
	implements ListMvpView<TYPE>, ListItemListener<TYPE> {

	private RecyclerView mRecyclerView;
	private ListMvpAdapter<TYPE, VIEWHOLDER> mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ProgressBar mProgressBar;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_list, container, false);

		setupAdapter();
		setupRecyclerView(view);
		setupSwipeRefreshLayout(view);
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_list_progress_bar);

		return view;
	}

	private void setupAdapter() {
		mAdapter = createAdapter();
		mAdapter.setListItemListener(this);
	}

	private void setupRecyclerView(View view) {
		mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_recycler_view);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void setupSwipeRefreshLayout(View view) {
		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_list_swipe_refresh);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);

		// TODO: Create SwipeRefreshObservable.
//		Observable<Void> swipeRefreshObservable = RxSwipeRefreshLayout.refreshes(mSwipeRefreshLayout).share();
		Observable<Void> swipeRefreshObservable = PublishSubject.create();

		getPresenter().setRefreshTrigger(swipeRefreshObservable
			.filter(aVoid -> ConnectivityUtils.isConnected(getApplicationContext())));

		swipeRefreshObservable
			.filter(aVoid -> !ConnectivityUtils.isConnected(getApplicationContext()))
			.subscribe(aVoid -> {
				mSwipeRefreshLayout.setRefreshing(false);
				ErrorDisplayer.displayError(getView(), new Throwable());
			});
	}

	protected abstract ListMvpAdapter<TYPE, VIEWHOLDER> createAdapter();

	@Override
	public void satDataList(List<TYPE> dataList) {
		mAdapter.setDataList(dataList);
	}

	@Override
	public void showLoadingView() {
		mProgressBar.setVisibility(View.VISIBLE);
		mRecyclerView.setVisibility(View.GONE);
	}

	@Override
	public void hideLoadingView() {
		mProgressBar.setVisibility(View.GONE);
		mRecyclerView.setVisibility(View.VISIBLE);
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showError(Throwable throwable) {
		ErrorDisplayer.displayError(getView(), throwable);
	}
}
