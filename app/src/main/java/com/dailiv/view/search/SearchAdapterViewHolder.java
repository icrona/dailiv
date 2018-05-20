package com.dailiv.view.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 3/31/18.
 */

@Getter
public class SearchAdapterViewHolder extends RecyclerView.ViewHolder{

    private LinearLayout layout;
    private TextView textView;
    private ImageView imageView;

    public SearchAdapterViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.ll_search);
        textView = itemView.findViewById(R.id.tv_search);
        imageView = itemView.findViewById(R.id.iv_search);
    }
}
