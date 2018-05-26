package com.dailiv.internal.data.local.pojo;

import android.text.SpannableString;

import com.dailiv.internal.data.remote.response.cart.CartResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.AssetUtil.getIngredientImageUrl;
import static com.dailiv.util.common.MoneyUtil.getMoneyString;

/**
 * Created by aldo on 4/28/18.
 */

@Data
@AllArgsConstructor
public class Cart {

    private int id;

    private int storeIngredientId;

    private String name;

    private int amount;

    private String unit;

    private int price;

    private String image;

    private int min;

    public String getImageUrl() {

        return getIngredientImageUrl(getImage());
    }

    public int getTotalPrice() {

        return getPrice() * getAmount();
    }

    public SpannableString getPriceString() {

        return getMoneyString(getPrice());
    }

    public SpannableString getTotalPriceString() {

        return getMoneyString(getTotalPrice());
    }

    public Cart(CartResponse cartResponse) {

        this(
                cartResponse.id,
                cartResponse.storeIngredientId,
                cartResponse.ingredient.name,
                cartResponse.amount,
                cartResponse.ingredient.unit,
                cartResponse.storeIngredient.price,
                cartResponse.ingredient.photo,
                cartResponse.storeIngredient.min
        );
    }

    public String getMinUnit() {

        return "/" + getMin() + " " + getUnit();
    }

}
