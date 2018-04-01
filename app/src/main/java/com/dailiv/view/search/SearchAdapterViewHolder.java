package com.dailiv.view.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 3/31/18.
 */

@Getter
public class SearchAdapterViewHolder extends RecyclerView.ViewHolder{

    private TextView textView;

    public SearchAdapterViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv_search);
    }
}
