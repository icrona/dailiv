package com.dailiv.view.shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;
import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by aldo on 4/7/18.
 */

@Getter
public class ShopAdapterViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout layout;
    private TextView ingredientName;
    private ImageView image;
    private LinearLayout quantityLayout;
    private QuantityView quantityView;
    private LinearLayout addToCartLayout;


    public ShopAdapterViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.rl_ingredient);
        ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
        image = itemView.findViewById(R.id.iv_ingredient);
        quantityLayout = itemView.findViewById(R.id.ll_qty);
        quantityView = itemView.findViewById(R.id.qv_shop);
        addToCartLayout = itemView.findViewById(R.id.ll_add_to_cart);
    }
}
