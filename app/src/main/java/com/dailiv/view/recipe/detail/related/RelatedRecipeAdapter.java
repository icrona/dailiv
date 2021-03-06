package com.dailiv.view.recipe.detail.related;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;
import rx.functions.Action2;

import static com.dailiv.util.common.GlideUtil.glide;

/**
 * Created by aldo on 5/6/18.
 */

@AllArgsConstructor
public class RelatedRecipeAdapter extends RecyclerView.Adapter<RelatedRecipeViewHolder>{

    @Setter
    private List<RecipeDetail.RelatedRecipe> relatedRecipes;

    private Action2<Class, String> navigateTo;

    @Override
    public RelatedRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related_recipe, parent, false);

        return new RelatedRecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RelatedRecipeViewHolder holder, int position) {

        holder.getName().setText(relatedRecipes.get(holder.getAdapterPosition()).getName());

        holder.getLayout().setBackgroundResource(getBackgroundId(holder.getAdapterPosition()));

        holder.getSeeRecipe().setOnClickListener(v -> navigateTo.call(RecipeDetailActivity.class, String.valueOf(relatedRecipes.get(holder.getAdapterPosition()).getSlug())));

        glide(holder.getImage(), relatedRecipes.get(holder.getAdapterPosition()).getImageUrl());

    }

    private int getBackgroundId(int position) {
        if(position % 2 == 0) {
            return R.drawable.bg_ingredient_left;
        }

        return R.drawable.bg_ingredient_right;
    }

    @Override
    public int getItemCount() {
        return relatedRecipes.size();
    }
}
