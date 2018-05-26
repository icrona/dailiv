package com.dailiv.view.cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;
import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by aldo on 4/28/18.
 */

@Getter
public class CartAdapterViewHolder extends RecyclerView.ViewHolder{

    private ImageView image;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvUnit;
    private TextView tvTotalPrice;
    private QuantityView qvCart;

    public CartAdapterViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.iv_cart);
        tvName = itemView.findViewById(R.id.tv_cart_name);
        tvPrice = itemView.findViewById(R.id.tv_cart_price);
        tvUnit = itemView.findViewById(R.id.tv_cart_unit);
        tvTotalPrice = itemView.findViewById(R.id.tv_cart_total_price);
        qvCart = itemView.findViewById(R.id.qv_cart);
    }
}
