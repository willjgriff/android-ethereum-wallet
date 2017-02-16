package com.github.willjgriff.ethereumwallet.ui.createaccount;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 15/02/2017.
 */

public class PreNavigationCreateAccountController extends Controller
	implements CreateAccountCompletedListener {

	@BindView(R.id.controller_create_account_pre_nav_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.controller_create_account_pre_nav_container)
	FrameLayout mCreateAccountContainer;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_create_account_pre_navigation, container, false);
		ButterKnife.bind(this, view);

		CreateAccountController createAccountController = new CreateAccountController();
		createAccountController.setTargetController(this);
		getChildRouter(mCreateAccountContainer)
			.pushController(RouterTransaction.with(createAccountController));

		mToolbar.setTitle(R.string.controller_create_account_title);

		return view;
	}

	@Override
	public void onAccountCreated() {
		getRouter().setRoot(RouterTransaction.with(new NavigationController())
			.pushChangeHandler(new FadeChangeHandler())
			.popChangeHandler(new FadeChangeHandler()));
	}
}
