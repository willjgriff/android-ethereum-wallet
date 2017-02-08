package com.github.willjgriff.ethereumwallet.ui.send;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.receive.ReceiveController;
import com.github.willjgriff.ethereumwallet.ui.send.di.DaggerSendComponent;
import com.github.willjgriff.ethereumwallet.ui.send.mvp.SendPresenter;
import com.github.willjgriff.ethereumwallet.ui.send.mvp.SendView;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class SendController extends BaseMvpController<SendView, SendPresenter> implements SendView {

	@BindView(R.id.controller_send_recipient_address)
	TextInputLayout mRecipientAddress;
	@BindView(R.id.controller_send_amount)
	TextInputLayout mSendAmount;
	@BindView(R.id.controller_send_password)
	TextInputLayout mAccountPassword;
	@BindView(R.id.controller_send_send_ether)
	Button mSendEtherButton;

	@Inject
	SendPresenter mSendPresenter;

	public SendController() {
		DaggerSendComponent.builder()
			.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
			.build().inject(this);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_send, container, false);
		ButterKnife.bind(this, view);

		setupToolbarTitle();
		setInputObservables();

//		getChildRouter(mInnerController)
//			.pushController(RouterTransaction.with(new CreateAccountController()));
		return view;
	}

	private void setupToolbarTitle() {
		if (getTargetController() instanceof NavigationToolbarListener) {
			((NavigationToolbarListener) getTargetController())
				.setToolbarTitle(getApplicationContext().getString(R.string.controller_send_title));
		}
	}

	private void setInputObservables() {
		Observable<CharSequence> recipientAddressObservable = RxTextView.textChanges(mRecipientAddress.getEditText());
		Observable<CharSequence> sendAmountObservable = RxTextView.textChanges(mSendAmount.getEditText());
		Observable<CharSequence> accountPasswordObservable = RxTextView.textChanges(mAccountPassword.getEditText());
		Observable<Object> sendObservable = RxView.clicks(mSendEtherButton);
		mSendPresenter.setObservables(recipientAddressObservable, sendAmountObservable, accountPasswordObservable, sendObservable);
	}

	@Override
	protected SendView getMvpView() {
		return this;
	}

	@Override
	protected SendPresenter getPresenter() {
		return mSendPresenter;
	}
}
