package com.dailiv.view.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 4/21/18.
 */

@Getter
public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder{

    private ImageView ivRecipe;
    private TextView tvRecipeName;

    public RecipeAdapterViewHolder(View itemView) {
        super(itemView);
        ivRecipe = itemView.findViewById(R.id.iv_recipe);
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
    }
}
