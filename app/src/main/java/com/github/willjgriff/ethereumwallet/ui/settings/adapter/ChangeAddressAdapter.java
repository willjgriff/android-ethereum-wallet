package com.github.willjgriff.ethereumwallet.ui.settings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.settings.adapter.ChangeAddressItemViewHolder.ChangeAddressItemListener;

import java.util.List;

import static com.github.willjgriff.ethereumwallet.ui.settings.adapter.ChangeAddressAdapter.ChangeAddressType.HEADER;
import static com.github.willjgriff.ethereumwallet.ui.settings.adapter.ChangeAddressAdapter.ChangeAddressType.ITEM;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int HEADER_OFFSET = 1;
	private List<AccountDelegate> mAccounts;
	private ChangeAddressItemListener mChangeAddressItemListener;

	public ChangeAddressAdapter(ChangeAddressItemListener changeAddressItemListener) {
		mChangeAddressItemListener = changeAddressItemListener;
	}

	public void setAccounts(List<AccountDelegate> accounts) {
		mAccounts = accounts;
	}

	enum ChangeAddressType {
		HEADER,
		ITEM
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		switch (ChangeAddressType.values()[viewType]) {
			case HEADER:
				return new ChangeAddressHeaderViewHolder(
					layoutInflater.inflate(R.layout.view_change_address_header, parent, false));
			case ITEM:
				return new ChangeAddressItemViewHolder(
					layoutInflater.inflate(R.layout.view_change_address_item, parent, false), mChangeAddressItemListener);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ChangeAddressItemViewHolder) {
			((ChangeAddressItemViewHolder) holder).bind(mAccounts.get(position - HEADER_OFFSET));
		}
	}

	@Override
	public int getItemCount() {
		return mAccounts.size() + HEADER_OFFSET;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return HEADER.ordinal();
		}
		return ITEM.ordinal();
	}
}
