package com.github.willjgriff.ethereumwallet.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

	@BindView(R.id.activity_main_controller_container)
	ViewGroup mControllerContainer;
	@BindView(R.id.activity_main_bottom_navigation)
	BottomNavigationView mBottomNavigationView;

	private Router mRouter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		ButterKnife.bind(this);

		setupInitialConductorRoot(savedInstanceState);
		setupBottomNavigationView();
	}

	private void setupInitialConductorRoot(Bundle savedInstanceState) {
		mRouter = Conductor.attachRouter(this, mControllerContainer, savedInstanceState);
		if (!mRouter.hasRootController()) {
			setConductorRoot(new TransactionsController());
		}
	}

	private void setupBottomNavigationView() {
		final NavigationControllerFactory navigationControllerFactory = new NavigationControllerFactory();
		mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Controller controller = navigationControllerFactory.getController(item);
				if (controller != null) {
					setConductorRoot(controller);
					return true;
				}
				return false;
			}
		});
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
