package com.dailiv.view.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.R;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;

/**
 * Created by aldo on 4/21/18.
 */

@Getter
public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout layout;
    private ImageView ivRecipe;
    private ImageView ivAddPlanning;
    private CircleImageView civRecipeUserPhoto;
    private TextView tvRecipeName;
    private TextView tvRecipeLike;
    private TextView tvRecipeView;
    private TextView tvRecipeInfo;

    public RecipeAdapterViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.rl_recipe);
        ivRecipe = itemView.findViewById(R.id.iv_recipe);
        ivAddPlanning = itemView.findViewById(R.id.iv_add_planning);
        civRecipeUserPhoto = itemView.findViewById(R.id.civ_recipe_user_photo);
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        tvRecipeLike = itemView.findViewById(R.id.tv_recipe_like);
        tvRecipeView = itemView.findViewById(R.id.tv_recipe_view);
        tvRecipeInfo = itemView.findViewById(R.id.tv_recipe_info);
    }
}
