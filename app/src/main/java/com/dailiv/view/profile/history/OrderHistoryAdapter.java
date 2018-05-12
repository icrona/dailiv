package com.dailiv.view.profile.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.OrderHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

/**
 * Created by aldo on 5/11/18.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapterViewHolder>{

    @Setter
    private List<OrderHistory> orderHistories = new ArrayList<>();

    @Override
    public OrderHistoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderHistoryAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapterViewHolder holder, int position) {

        holder.getTvAddress().setText(orderHistories.get(holder.getAdapterPosition()).getAddress());

        holder.getTvDate().setText(orderHistories.get(holder.getAdapterPosition()).getDate());

        holder.getTvAmount().setText(orderHistories.get(holder.getAdapterPosition()).getAmountString());

        holder.getTvStatus().setText(orderHistories.get(holder.getAdapterPosition()).getStatus());
    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }
}
