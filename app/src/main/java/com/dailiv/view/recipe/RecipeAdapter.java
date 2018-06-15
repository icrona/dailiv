package com.dailiv.view.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.view.profile.other.OtherProfileActivity;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;

import java.util.List;

import rx.functions.Action1;
import rx.functions.Action2;

import static com.dailiv.App.getContext;
import static com.dailiv.util.common.GlideUtil.glide;
import static com.dailiv.util.common.GlideUtil.glideRounded;

/**
 * Created by aldo on 4/21/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapterViewHolder>{

    private List<RecipeIndex> recipes;

    private Action1<Integer> addToMealPlanning;

    private Action2<Class, String> navigateTo;

    private int radius;

    public RecipeAdapter(List<RecipeIndex> recipes, Action1<Integer> addToMealPlanning, Action2<Class, String> navigateTo) {
        this.recipes = recipes;
        this.addToMealPlanning = addToMealPlanning;
        this.navigateTo = navigateTo;

        radius = getContext().getResources().getDimensionPixelOffset(R.dimen.xs);
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {

        glideRounded(holder.getIvRecipe(), recipes.get(holder.getAdapterPosition()).getImageUrl());

        if(addToMealPlanning == null) {

            holder.getIvAddPlanning().setVisibility(View.GONE);
        }
        else{

            holder.getIvAddPlanning().setVisibility(View.VISIBLE);
        }

        holder.getIvAddPlanning().setOnClickListener(v -> {
            addToMealPlanning.call(recipes.get(holder.getAdapterPosition()).getId());
        });

        holder.getLayout().setOnClickListener(v -> navigateTo.call(RecipeDetailActivity.class, recipes.get(holder.getAdapterPosition()).getSlug()));

        holder.getTvRecipeName().setText(recipes.get(holder.getAdapterPosition()).getRecipeName());

        glide(holder.getCivRecipeUserPhoto(), recipes.get(holder.getAdapterPosition()).getUserPhotoUrl());

        holder.getCivRecipeUserPhoto().setOnClickListener(v -> navigateTo.call(OtherProfileActivity.class, recipes.get(holder.getAdapterPosition()).getUserSlug()));


        holder.getTvRecipeLike().setText(String.valueOf(recipes.get(holder.getAdapterPosition()).getLike()));

        holder.getTvRecipeView().setText(String.valueOf(recipes.get(holder.getAdapterPosition()).getView()));

        holder.getTvRecipeInfo().setText(recipes.get(holder.getAdapterPosition()).getInfo());
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
