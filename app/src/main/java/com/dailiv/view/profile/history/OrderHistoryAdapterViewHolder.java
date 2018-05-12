package com.dailiv.view.profile.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 5/11/18.
 */

@Getter
public class OrderHistoryAdapterViewHolder extends RecyclerView.ViewHolder{

    private TextView tvAddress;
    private TextView tvDate;
    private TextView tvAmount;
    private TextView tvStatus;

    public OrderHistoryAdapterViewHolder(View itemView) {
        super(itemView);
        tvAddress = itemView.findViewById(R.id.tv_address);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvStatus = itemView.findViewById(R.id.tv_status);

    }
}
