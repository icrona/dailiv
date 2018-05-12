package com.dailiv.view.profile.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 5/12/18.
 */

@Getter
public class ProfileMenuAdapterViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout layout;

    private TextView tvName;

    private ImageView ivIcon;

    public ProfileMenuAdapterViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.rl_menu_layout);
        tvName = itemView.findViewById(R.id.tv_menu_name);
        ivIcon = itemView.findViewById(R.id.iv_menu_icon);
    }
}
