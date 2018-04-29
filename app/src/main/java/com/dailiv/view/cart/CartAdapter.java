package com.dailiv.view.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.himanshusoni.quantityview.QuantityView;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by aldo on 4/28/18.
 */

@AllArgsConstructor
public class CartAdapter extends RecyclerView.Adapter<CartAdapterViewHolder>{

    @Getter
    @Setter
    private List<Cart> cartList;

    private Action1<Integer> deleteCart;

    private Action2<Integer, Integer> updateCart;


    @Override
    public CartAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartAdapterViewHolder holder, int position) {

        holder.getTvName().setText(cartList.get(holder.getAdapterPosition()).getName());

        holder.getTvPrice().setText(cartList.get(holder.getAdapterPosition()).getPriceString());

        holder.getTvTotalPrice().setText(cartList.get(holder.getAdapterPosition()).getTotalPriceString());

        Glide.get(holder.getImage().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getImage().getContext())
                .load(cartList.get(holder.getAdapterPosition()).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(holder.getImage());

        holder.getQvCart().setQuantity(cartList.get(holder.getAdapterPosition()).getAmount());

        holder.getQvCart().setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

                int cartId = cartList.get(holder.getAdapterPosition()).getId();

                if(newQuantity == 0) {

                    deleteCart.call(cartId);
                    cartList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
                else{
                    Cart cart = cartList.get(holder.getAdapterPosition());
                    updateCart.call(cartId, newQuantity);
                    cart.setAmount(newQuantity);
                    cartList.set(holder.getAdapterPosition(), cart);

                    notifyItemChanged(holder.getAdapterPosition(), cart);
                }


            }

            @Override
            public void onLimitReached() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public int getGrandTotal() {

        return Stream.of(getCartList())
                .map(Cart::getTotalPrice)
                .reduce(0, (a, b) -> a + b);
    }
}
