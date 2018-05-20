package com.dailiv.view.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.view.custom.RoundedCornersTransformation;

import java.util.List;

import rx.functions.Action1;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 4/21/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapterViewHolder>{

    private List<RecipeIndex> recipes;

    private Action1<Integer> addToMealPlanning;

    private Action1<String> navigateTo;

    private int radius;

    public RecipeAdapter(List<RecipeIndex> recipes, Action1<Integer> addToMealPlanning, Action1<String> navigateTo) {
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

        Glide.get(holder.getIvRecipe().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getIvRecipe().getContext())
                .load(recipes.get(holder.getAdapterPosition()).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .bitmapTransform(new RoundedCornersTransformation(getContext(), radius, 0))

                .into(holder.getIvRecipe());

        if(addToMealPlanning == null) {

            holder.getIvAddPlanning().setVisibility(View.GONE);
        }
        else{

            holder.getIvAddPlanning().setVisibility(View.VISIBLE);
        }

        holder.getIvAddPlanning().setOnClickListener(v -> {
            addToMealPlanning.call(recipes.get(holder.getAdapterPosition()).getId());
        });

        holder.getLayout().setOnClickListener(v -> navigateTo.call(recipes.get(holder.getAdapterPosition()).getSlug()));

        holder.getTvRecipeName().setText(recipes.get(holder.getAdapterPosition()).getRecipeName());

        Glide.get(holder.getCivRecipeUserPhoto().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getCivRecipeUserPhoto().getContext())
                .load(recipes.get(holder.getAdapterPosition()).getUserPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_account)
                .error(R.mipmap.ic_account)
                .dontAnimate()
                .into(holder.getCivRecipeUserPhoto());

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
