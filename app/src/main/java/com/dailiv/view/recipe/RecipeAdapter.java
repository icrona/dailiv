package com.dailiv.view.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeIndex;

import java.util.List;

import lombok.AllArgsConstructor;
import rx.functions.Action1;

/**
 * Created by aldo on 4/21/18.
 */

@AllArgsConstructor
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapterViewHolder>{

    private List<RecipeIndex> recipes;

    private Action1<Integer> addToMealPlanning;

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {

        Glide.get(holder.getIvRecipe().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getIvRecipe().getContext())
                .load(recipes.get(holder.getAdapterPosition()).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(holder.getIvRecipe());

        holder.getTvRecipeName().setText(recipes.get(holder.getAdapterPosition()).getRecipeName());

        holder.getBtnAddPlanning().setOnClickListener(v -> {
            addToMealPlanning.call(recipes.get(holder.getAdapterPosition()).getId());
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<RecipeIndex> recipes) {
        this.recipes.addAll(recipes);
    }

    public void clearRecipes() {
        this.recipes.clear();
    }
}
