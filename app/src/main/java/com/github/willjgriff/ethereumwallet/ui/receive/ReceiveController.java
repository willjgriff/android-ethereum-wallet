package com.github.willjgriff.ethereumwallet.ui.receive;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.receive.di.ReceiveInjector;
import com.github.willjgriff.ethereumwallet.ui.receive.mvp.ReceivePresenter;
import com.github.willjgriff.ethereumwallet.ui.receive.mvp.ReceiveView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 29/01/2017.
 */

public class ReceiveController extends BaseMvpController<ReceiveView, ReceivePresenter>
	implements ReceiveView {

	@BindView(R.id.controller_receive_ethereum_address)
	TextView mReceiveAddress;

	@Inject
	ReceivePresenter mReceivePresenter;

	public ReceiveController() {
		ReceiveInjector.INSTANCE.injectNewReceivePresenter(this);
	}

	@Override
	protected ReceiveView getMvpView() {
		return this;
	}

	@Override
	protected ReceivePresenter createPresenter() {
		return mReceivePresenter;
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_receive, container, false);
		ButterKnife.bind(this, view);
		setupToolbarTitle();
		return view;
	}

	private void setupToolbarTitle() {
		if (getTargetController() instanceof NavigationToolbarListener) {
			((NavigationToolbarListener) getTargetController())
				.setToolbarTitle(getApplicationContext().getString(R.string.controller_receive_title));
		}
	}

	public void setReceiveAddress(String address) {
		mReceiveAddress.setText(address);
	}
}
