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

import java.util.List;

import lombok.AllArgsConstructor;
import me.himanshusoni.quantityview.QuantityView;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by aldo on 4/7/18.
 */

@AllArgsConstructor
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapterViewHolder>{

    private List<IngredientIndex> ingredients;

//    private Action0 addToCart;

    private Action1<Integer> addToCartDummy;

    private Action1<Integer> deleteCart;

    private Action2<Integer, Integer> updateCart;


    @Override
    public ShopAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopAdapterViewHolder holder, int position) {

        holder.getIngredientName().setText(ingredients.get(holder.getAdapterPosition()).getName());

        holder.getLayout().setBackgroundResource(getBackgroundId(holder.getAdapterPosition()));

        Glide.get(holder.getImage().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getImage().getContext())
                .load(ingredients.get(holder.getAdapterPosition()).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(holder.getImage());

        holder.getAddToCartLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                holder.getQuantityLayout().setVisibility(View.VISIBLE);
                holder.getQuantityView().setQuantity(1);
                //add to cart

                addToCartDummy.call(holder.getAdapterPosition());
            }
        });

        holder.getQuantityView().setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

                int cartId = ingredients.get(holder.getAdapterPosition()).getCartId();
                if(newQuantity == 0) {
                    holder.getQuantityLayout().setVisibility(View.GONE);
                    holder.getAddToCartLayout().setVisibility(View.VISIBLE);
                    deleteCart.call(cartId);
                }
                else{
                    updateCart.call(cartId, newQuantity);
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

    }

    private int getBackgroundId(int position) {
        if(position % 2 == 0) {
            return R.drawable.bg_ingredient_left;
        }

        return R.drawable.bg_ingredient_right;
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

    public void clearIngredients() {
        this.ingredients.clear();
    }

}
