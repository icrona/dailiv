package com.dailiv.view.shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 4/7/18.
 */

@Getter
public class ShopAdapterViewHolder extends RecyclerView.ViewHolder{

    private LinearLayout layout;
    private TextView ingredientName;
    private ImageView image;

    public ShopAdapterViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.ll_ingredient);
        ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
        image = itemView.findViewById(R.id.iv_ingredient);
    }
}
