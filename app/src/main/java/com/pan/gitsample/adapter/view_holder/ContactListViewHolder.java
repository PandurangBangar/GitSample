package com.pan.gitsample.adapter.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.pan.gitsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Pandurang.
 */
public class ContactListViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.text_view_name)
    public TextView textViewName;

    @BindView(R.id.image_view_user_image)
    public ImageView imageViewUserImage;

    @BindView(R.id.text_view_email)
    public TextView textViewEmail;

    public ContactListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
