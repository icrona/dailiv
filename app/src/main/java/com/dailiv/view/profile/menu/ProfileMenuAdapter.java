package com.dailiv.view.profile.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.ProfileMenu;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Created by aldo on 5/12/18.
 */
@AllArgsConstructor
public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapterViewHolder>{

    private List<ProfileMenu> profileMenus;

    @Override
    public ProfileMenuAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_menu, parent, false);
        return new ProfileMenuAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileMenuAdapterViewHolder holder, int position) {

        holder.getLayout().setOnClickListener(v -> profileMenus.get(holder.getAdapterPosition()).getProfileAction().call());

        holder.getTvName().setText(profileMenus.get(holder.getAdapterPosition()).getName());

        holder.getIvIcon().setImageResource(profileMenus.get(holder.getAdapterPosition()).getIconResId());

    }

    @Override
    public int getItemCount() {
        return profileMenus.size();
    }
}
