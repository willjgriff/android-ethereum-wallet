package com.github.willjgriff.ethereumwallet.ui.settings;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wiljgriff.ethereumwallet.data.model.DomainAddress;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.settings.adapter.ChangeAddressItemViewHolder.ChangeAddressItemListener;
import com.github.willjgriff.ethereumwallet.ui.settings.adapter.ChangeAddressAdapter;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.ChangeAddressPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.ChangeAddressView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 22/02/2017.
 */

public class ChangeAddressController extends BaseMvpController<ChangeAddressView, ChangeAddressPresenter>
	implements ChangeAddressView, ChangeAddressItemListener {

	@BindView(R.id.controller_settings_change_address_recycler_view)
	RecyclerView mRecyclerView;
	private ChangeAddressAdapter mAdapter;

	@Inject
	ChangeAddressPresenter mPresenter;

	public ChangeAddressController() {
		SettingsInjector.INSTANCE.injectNewSettingsChangeAddressPresenter(this);
	}

	@Override
	protected ChangeAddressView getMvpView() {
		return this;
	}

	@Override
	protected ChangeAddressPresenter createPresenter() {
		return mPresenter;
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_settings_change_address, container, false);
		ButterKnife.bind(this, view);

		mAdapter = new ChangeAddressAdapter(this);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		mRecyclerView.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void setAddresses(List<DomainAddress> allAccounts) {
		mAdapter.setAccounts(allAccounts);
	}

	@Override
	public void closeScreen() {
		getRouter().popCurrentController();
	}

	@Override
	public void addressItemClicked(DomainAddress domainAddress) {
		getPresenter().onAddressItemClicked(domainAddress);
	}
}
