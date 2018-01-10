package com.pan.gitsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pan.gitsample.R;
import com.pan.gitsample.adapter.view_holder.ContactListViewHolder;
import com.pan.gitsample.interfaces.OnItemClickListener;
import com.pan.gitsample.models.User;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Pandurang.
 */
public class ContactListAdapter extends RecyclerView.Adapter implements View.OnClickListener {


    private ArrayList<User> mList;
    private ArrayList<User> listForSearch;
    private OnItemClickListener mOnItemClickListener;
    private Context context;

    public ContactListAdapter(Context context, ArrayList<User> list) {
        this.mList = list;
        this.context = context;
        this.listForSearch = new ArrayList<>();
        this.listForSearch.addAll(mList);

    }


    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        ContactListViewHolder mViewHolder = new ContactListViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vholder, int position) {

        if (vholder instanceof ContactListViewHolder) {
            ContactListViewHolder holder = (ContactListViewHolder) vholder;


            holder.textViewName.setText(TextUtils.isEmpty(mList.get(position).getName()) ? "-" : mList.get(position).getName());
            holder.textViewEmail.setText(TextUtils.isEmpty(mList.get(position).getEmail()) ? "-" : mList.get(position).getEmail());


            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);

        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());

    }

    public User getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    public ArrayList<User> getListData() {
        return this.mList;
    }

    public void setListData(ArrayList<User> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mList.clear();
        if (charText.length() == 0) {
            mList.addAll(listForSearch);
        } else {
            for (User wp : listForSearch) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
