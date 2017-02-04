package com.github.willjgriff.ethereumwallet.ui.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 29/01/2017.
 */

public class NavigationController extends Controller
	implements NavigationToolbarListener {

	@BindView(R.id.controller_create_account_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.controller_navigation_balance)
	TextView mBalance;
	@BindView(R.id.controller_navigation_container)
	ViewGroup mControllerContainer;
	@BindView(R.id.controller_navigation_bottom_navigation)
	BottomNavigationView mBottomNavigationView;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_navigation, container, false);
		ButterKnife.bind(this, view);

		if (!getChildRouter(mControllerContainer).hasRootController()) {
			switchToController(new TransactionsController());
		}

		setupBottomNavigationView();
		return view;
	}

	private void switchToController(Controller controller) {
		controller.setTargetController(this);
		getChildRouter(mControllerContainer)
			.setRoot(RouterTransaction.with(controller)
				.pushChangeHandler(new FadeChangeHandler())
				.popChangeHandler(new FadeChangeHandler()));
	}

	private void setupBottomNavigationView() {
		final NavigationControllerFactory navigationControllerFactory = new NavigationControllerFactory();
		mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			Controller controller = navigationControllerFactory.getController(item);
			if (controller != null) {
				switchToController(controller);
				return true;
			}
			return false;
		});
	}

	@Override
	public void setToolbarTitle(CharSequence toolbarTitle) {
		mToolbar.setTitle(toolbarTitle);
	}

	@Override
	public void setBalance(double balance) {
		mBalance.setText(String.valueOf(balance));
	}
}
