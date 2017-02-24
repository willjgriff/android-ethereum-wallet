package com.github.willjgriff.ethereumwallet.ui.createaccount;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

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
	TextInputLayout mPassword;
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
		Observable<CharSequence> passwordObservable = RxTextView.textChanges(mPassword.getEditText());
		Observable<Object> submitButtonObservable = RxView.clicks(mSubmitButton);
		return mCreateAccountPresenterFactory.create(passwordObservable, submitButtonObservable);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_create_account, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void hidePasswordError() {
		mPassword.setErrorEnabled(false);
	}

	@Override
	public void showPasswordError() {
		mPassword.setError(getApplicationContext().getString(R.string.controller_create_account_enter_password_error));
	}

	@Override
	public void openWallet() {
		if (getTargetController() instanceof CreateAccountCompletedListener) {
			((CreateAccountCompletedListener) getTargetController()).onAccountCreated();
		}
	}
}
