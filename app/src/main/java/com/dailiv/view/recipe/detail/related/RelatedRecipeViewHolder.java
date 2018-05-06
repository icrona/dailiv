package com.dailiv.view.recipe.detail.related;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 5/6/18.
 */

@Getter
public class RelatedRecipeViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout layout;
    private TextView name;
    private ImageView image;
    private TextView seeRecipe;

    public RelatedRecipeViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.rl_related_recipe);
        name = itemView.findViewById(R.id.tv_related_recipe_name);
        image = itemView.findViewById(R.id.iv_related_recipe);
        seeRecipe = itemView.findViewById(R.id.tv_see_recipe);
    }
}
