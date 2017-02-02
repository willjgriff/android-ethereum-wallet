package com.github.willjgriff.ethereumwallet.ui.send;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.receive.ReceiveController;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class SendController extends RxController {

	@BindView(R.id.controller_send_inner_controller)
	FrameLayout mInnerController;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_send, container, false);
		ButterKnife.bind(this, view);

		getChildRouter(mInnerController)
			.pushController(RouterTransaction.with(new CreateAccountController()));

		return view;
	}
}
