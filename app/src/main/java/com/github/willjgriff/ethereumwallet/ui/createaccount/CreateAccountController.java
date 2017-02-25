package com.github.willjgriff.ethereumwallet.ui.createaccount;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.di.CreateAccountInjector;
import com.github.willjgriff.ethereumwallet.ui.createaccount.mvp.CreateAccountPresenter;
import com.github.willjgriff.ethereumwallet.ui.createaccount.mvp.CreateAccountPresenterFactory;
import com.github.willjgriff.ethereumwallet.ui.createaccount.mvp.CreateAccountView;
import com.github.willjgriff.ethereumwallet.ui.widget.validated.ValidatedTextInputLayout;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Will on 30/01/2017.
 */

public class CreateAccountController extends BaseMvpController<CreateAccountView, CreateAccountPresenter>
	implements CreateAccountView {

	@BindView(R.id.controller_create_account_password_text_input)
	ValidatedTextInputLayout mPassword;
	@BindView(R.id.controller_create_account_create_button)
	Button mSubmitButton;

	@Inject
	CreateAccountPresenterFactory mCreateAccountPresenterFactory;

	public CreateAccountController() {
		CreateAccountInjector.INSTANCE.injectPresenterFactory(this);
	}

	@Override
	protected CreateAccountView getMvpView() {
		return this;
	}

	@Override
	protected CreateAccountPresenter createPresenter() {
		Observable<String> passwordChanged = mPassword.getTextChangedObservable();
		Observable<Boolean> passwordValid = mPassword.getTextValidObservable();
		Observable<Object> submitButtonObservable = RxView.clicks(mSubmitButton).share();
		mPassword.setCheckValidationTrigger(submitButtonObservable);
		return mCreateAccountPresenterFactory.create(passwordChanged, passwordValid, submitButtonObservable);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_create_account, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void openWallet() {
		if (getTargetController() instanceof CreateAccountCompletedListener) {
			((CreateAccountCompletedListener) getTargetController()).onAccountCreated();
		}
	}
}
