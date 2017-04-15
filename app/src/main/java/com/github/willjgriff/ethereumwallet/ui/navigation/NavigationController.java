package com.github.willjgriff.ethereumwallet.ui.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.wiljgriff.ethereumwallet.ui.transactions.TransactionsController;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentsInvalidator;
import com.github.willjgriff.ethereumwallet.ui.navigation.di.DaggerNavigationComponent;
import com.github.willjgriff.ethereumwallet.ui.utils.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 29/01/2017.
 */

public class NavigationController extends Controller
	implements NavigationToolbarListener {

	private static final String STATE_SELECTED_ITEM = NavigationToolbarListener.class.getName() + ";STATE_SELECTED_ITEM";

	@BindView(R.id.controller_navigation_toolbar)
	Toolbar mToolbarTitle;
	@BindView(R.id.controller_navigation_balance)
	TextView mBalance;
	@BindView(R.id.controller_navigation_container)
	ViewGroup mControllerContainer;
	@BindView(R.id.controller_navigation_bottom_navigation)
	BottomNavigationView mBottomNavigationView;

	// TODO: See if there is a better way of doing this / find it manually each time it's needed.
	private int mCurrentlySelectedMenuItemId = 0;

	@Inject
	ComponentsInvalidator mComponentsInvalidator;

	public NavigationController() {
		DaggerNavigationComponent.builder()
			.build()
			.inject(this);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_navigation, container, false);
		ButterKnife.bind(this, view);

		// This is currently necessary because if the Activity gets reclaimed by the OS we loose the
		// position on the BottomNavigationMenu. Good job BottomNav...
		if (mCurrentlySelectedMenuItemId != 0) {
			mBottomNavigationView.getMenu().findItem(mCurrentlySelectedMenuItemId).setChecked(true);
		}

		if (!getChildRouter(mControllerContainer).hasRootController()) {
			switchToController(new TransactionsController());
		}

		setupBottomNavigationView();
		return view;
	}

	private void setupBottomNavigationView() {
		final NavigationControllerFactory navigationControllerFactory = new NavigationControllerFactory();
		mBottomNavigationView.setOnNavigationItemSelectedListener(item ->
			onBottomNavItemClicked(navigationControllerFactory, item));
	}

	private boolean onBottomNavItemClicked(NavigationControllerFactory navigationControllerFactory, MenuItem item) {
		mCurrentlySelectedMenuItemId = item.getItemId();
		UiUtils.hideSoftKeyboard(getView());
		Controller controller = navigationControllerFactory.getController(item);
		if (controller != null) {
			switchToController(controller);
			return true;
		}
		return false;
	}

	private void switchToController(Controller controller) {
		mComponentsInvalidator.invalidateComponents();
		controller.setTargetController(this);
		getChildRouter(mControllerContainer)
			.setRoot(RouterTransaction.with(controller)
				.pushChangeHandler(new FadeChangeHandler())
				.popChangeHandler(new FadeChangeHandler()));
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_ITEM, mCurrentlySelectedMenuItemId);
	}

	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCurrentlySelectedMenuItemId = savedInstanceState.getInt(STATE_SELECTED_ITEM);
	}

	@Override
	public void setToolbarTitle(CharSequence toolbarTitle) {
		// TODO: Animate the changing of this title to match the FadeChangeHandler of the Controllers.
		mToolbarTitle.setTitle(toolbarTitle);
	}

	@Override
	public void setBalance(String balance) {
		mBalance.setText(balance);
	}
}
