package com.github.willjgriff.ethereumwallet.ui.receive;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.github.willjgriff.ethereumwallet.R;

/**
 * Created by Will on 29/01/2017.
 */

public class ReceiveController extends RxController {

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_receive, container, false);
	}
}
