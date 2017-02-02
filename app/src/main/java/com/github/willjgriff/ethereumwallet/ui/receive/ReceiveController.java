package com.github.willjgriff.ethereumwallet.ui.receive;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

/**
 * Created by Will on 29/01/2017.
 */

public class ReceiveController extends Controller {

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_receive, container, false);
		view.findViewById(R.id.controller_receive_detail_button)
			.setOnClickListener(buttonView -> getRouter()
				.pushController(RouterTransaction.with(new TransactionsController())));

		return view;
	}
}
