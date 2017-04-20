package com.github.willjgriff.ethereumwallet.ui.settings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress;
import com.github.willjgriff.ethereumwallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressItemViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.view_change_address_item_address)
	TextView mAddress;

	private ChangeAddressItemListener mChangeAddressItemListener;

	public ChangeAddressItemViewHolder(View itemView, ChangeAddressItemListener changeAddressItemListener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		mChangeAddressItemListener = changeAddressItemListener;
	}

	public void bind(DomainAddress domainAddress) {
		mAddress.setText(domainAddress.getHex());
		itemView.setOnClickListener(view -> mChangeAddressItemListener.addressItemClicked(domainAddress));
	}

	public interface ChangeAddressItemListener {
		void addressItemClicked(DomainAddress domainAddress);
	}
}
