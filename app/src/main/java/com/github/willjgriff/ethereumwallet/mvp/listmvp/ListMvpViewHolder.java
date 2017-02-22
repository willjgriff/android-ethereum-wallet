package com.github.willjgriff.ethereumwallet.mvp.listmvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Will on 24/11/2016.
 */

public abstract class ListMvpViewHolder<TYPE> extends RecyclerView.ViewHolder {

	private ListItemListener<TYPE> mListItemListener;

	public ListMvpViewHolder(View itemView, ListItemListener<TYPE> listItemListener) {
		super(itemView);
		mListItemListener = listItemListener;
	}

	public void bind(TYPE data) {
		itemView.setOnClickListener(view -> mListItemListener.onItemClick(data));
		bindData(data);
	}

	public abstract void bindData(TYPE data);

	public interface ListItemListener<TYPE> {
		void onItemClick(TYPE data);
	}
}
