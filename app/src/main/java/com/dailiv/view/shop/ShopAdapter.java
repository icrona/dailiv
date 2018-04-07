package com.dailiv.view.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.IngredientIndex;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Created by aldo on 4/7/18.
 */

@AllArgsConstructor
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapterViewHolder>{

    private List<IngredientIndex> ingredients;

    @Override
    public ShopAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopAdapterViewHolder holder, int position) {

        holder.getIngredientName().setText(ingredients.get(position).getName());

        holder.getLayout().setBackgroundResource(getBackgroundId(position));

        Glide.get(holder.getImage().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getImage().getContext())
                .load(ingredients.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(holder.getImage());

    }

    private int getBackgroundId(int position) {
        position +=3;
        if(position % 3 == 0) {
            return R.drawable.bg_ingredient_left;
        }
        if(position % 3 == 2) {
            return R.drawable.bg_ingredient_right;
        }

        return R.drawable.bg_ingredient;
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public ShopAdapter() {
        this.ingredients = new ArrayList<>();
    }

    public void setIngredients(List<IngredientIndex> ingredients) {
        this.ingredients.addAll(ingredients);
    }

}
