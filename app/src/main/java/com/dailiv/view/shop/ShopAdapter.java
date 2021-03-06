package com.dailiv.view.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.IngredientIndex;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.himanshusoni.quantityview.QuantityView;
import rx.functions.Action1;
import rx.functions.Action2;

import static com.dailiv.util.common.GlideUtil.glide;

/**
 * Created by aldo on 4/7/18.
 */

@AllArgsConstructor
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapterViewHolder>{

    private List<IngredientIndex> ingredients;

    private Action1<Integer> addToCart;

    private Action1<Integer> deleteCart;

    private Action2<Integer, Integer> updateCart;

    private Action1<String> navigateTo;

    private Action1<Integer> setCountChanges;

    @Override
    public ShopAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopAdapterViewHolder holder, int position) {

        holder.getIngredientName().setText(ingredients.get(holder.getAdapterPosition()).getName());

        holder.getTvPrice().setText(ingredients.get(holder.getAdapterPosition()).getPriceString());

        holder.getTvMinUnit().setText(ingredients.get(holder.getAdapterPosition()).getMinUnit());

        holder.getLayout().setBackgroundResource(getBackgroundId(holder.getAdapterPosition()));

        holder.getQuantityLayout().setBackgroundResource(getAddToCartBackground(holder.getAdapterPosition()));

        holder.getLayout().setOnClickListener(v -> navigateTo.call(String.valueOf(ingredients.get(holder.getAdapterPosition()).getId())));

        glide(holder.getImage(), ingredients.get(holder.getAdapterPosition()).getImageUrl());

        holder.getAddToCartLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                holder.getQuantityLayout().setVisibility(View.VISIBLE);
                holder.getQuantityView().setQuantity(1);

                IngredientIndex ingredientIndex = ingredients.get(holder.getAdapterPosition());
                ingredientIndex.setCartedAmount(1);
                ingredients.set(holder.getAdapterPosition(), ingredientIndex);

                addToCart.call(ingredients.get(holder.getAdapterPosition()).getStoreIngredientId());

                setCountChanges.call(1);
            }
        });

        holder.getQuantityView().setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

                Integer cartId = ingredients.get(holder.getAdapterPosition()).getCartId();

                IngredientIndex ingredientIndex = ingredients.get(holder.getAdapterPosition());
                ingredientIndex.setCartedAmount(newQuantity);
                ingredients.set(holder.getAdapterPosition(), ingredientIndex);

                if(newQuantity == 0) {
                    holder.getQuantityLayout().setVisibility(View.GONE);
                    holder.getAddToCartLayout().setVisibility(View.VISIBLE);
                    deleteCart.call(cartId);
                    holder.getLayout().setBackgroundResource(getBackgroundId(holder.getAdapterPosition()));
                }
                else{
                    updateCart.call(cartId, newQuantity);
                }

                setCountChanges.call(newQuantity - oldQuantity);

            }

            @Override
            public void onLimitReached() {

            }
        });

        if(ingredients.get(position).isCarted()){
            holder.getAddToCartLayout().setVisibility(View.GONE);
            holder.getQuantityLayout().setVisibility(View.VISIBLE);
            holder.getQuantityView().setQuantity(ingredients.get(position).getCartedAmount());
            holder.getLayout().setBackgroundResource(getBackgroundIdActive(holder.getAdapterPosition()));

        }
        else{
            holder.getAddToCartLayout().setVisibility(View.VISIBLE);
            holder.getQuantityLayout().setVisibility(View.GONE);
            holder.getQuantityView().setQuantity(0);
            holder.getLayout().setBackgroundResource(getBackgroundId(holder.getAdapterPosition()));

        }

    }

    private int getBackgroundId(int position) {
        if(position % 2 == 0) {
            return R.drawable.bg_ingredient_left;
        }

        return R.drawable.bg_ingredient_right;
    }

    private int getBackgroundIdActive(int position) {
        if(position % 2 == 0) {
            return R.drawable.bg_ingredient_left_active;
        }

        return R.drawable.bg_ingredient_right_active;
    }

    private int getAddToCartBackground(int position) {

        if(position % 2 == 0) {
            return R.drawable.bg_add_to_cart_left;
        }

        return R.drawable.bg_add_to_cart_right;
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    public void setIngredients(List<IngredientIndex> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    public void updateIngredients(int position, IngredientIndex ingredient) {

        this.ingredients.set(position, ingredient);
    }

    public void updateIngredients(List<IngredientIndex> ingredients) {

        this.ingredients = ingredients;
    }

    public void clearIngredients() {
        this.ingredients.clear();
    }

}
