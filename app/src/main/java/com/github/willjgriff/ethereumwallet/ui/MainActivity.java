package com.github.willjgriff.ethereumwallet.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.wiljgriff.ethereumwallet.di.AppInjector;
import com.github.wiljgriff.ethereumwallet.ethereum.account.AddressManager;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.createaccount.PreNavigationCreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.activity_main_controller_container)
	ViewGroup mControllerContainer;

	@Inject
	AddressManager mAddressManager;

	private Router mRouter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppInjector.appComponent.inject(this);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mRouter = Conductor.attachRouter(this, mControllerContainer, savedInstanceState);
		if (!mRouter.hasRootController()) {
			checkForAccountAndSetRoot();
		}
	}

	private void checkForAccountAndSetRoot() {
		if (mAddressManager.hasAddress()) {
			setConductorRoot(new NavigationController());
		} else {
			setConductorRoot(new PreNavigationCreateAccountController());
		}
	}

	private void setConductorRoot(Controller controller) {
		mRouter.setRoot(RouterTransaction.with(controller)
			.pushChangeHandler(new FadeChangeHandler())
			.popChangeHandler(new FadeChangeHandler()));
	}

	@Override
	public void onBackPressed() {
		if (!mRouter.handleBack()) {
			super.onBackPressed();
		}
	}
}
