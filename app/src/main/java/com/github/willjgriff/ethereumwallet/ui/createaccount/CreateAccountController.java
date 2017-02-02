package com.github.willjgriff.ethereumwallet.ui.createaccount;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bluelinelabs.conductor.Controller;
import com.github.willjgriff.ethereumwallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 30/01/2017.
 */

public class CreateAccountController extends Controller {

	@BindView(R.id.controller_create_account_password)
	EditText mPassword;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_create_account, container, false);
		ButterKnife.bind(this, view);
		return view;
	}
}
