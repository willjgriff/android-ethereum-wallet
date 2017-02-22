package com.github.willjgriff.ethereumwallet.mvp.listmvp;

import android.support.v7.widget.RecyclerView;

import com.github.willjgriff.ethereumwallet.mvp.listmvp.ListMvpViewHolder.ListItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 23/11/2016.
 */

public abstract class ListMvpAdapter<TYPE, VIEWHOLDER extends ListMvpViewHolder<TYPE>> extends RecyclerView.Adapter<VIEWHOLDER> {

	protected ListItemListener<TYPE> mListItemListener;
	private List<TYPE> mDataList = new ArrayList<>();

	public void setListItemListener(ListItemListener<TYPE> listItemListener) {
		mListItemListener = listItemListener;
	}

	public void setDataList(List<TYPE> dataList) {
		mDataList = dataList;
		notifyDataSetChanged();
	}

	@Override
	public void onBindViewHolder(VIEWHOLDER holder, int position) {
		holder.bind(mDataList.get(position));
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

}
