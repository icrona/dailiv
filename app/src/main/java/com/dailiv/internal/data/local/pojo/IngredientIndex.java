package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.ingredient.Ingredient;
import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.AssetUtil.getIngredientImageUrl;

/**
 * Created by aldo on 4/1/18.
 */

@Data
@AllArgsConstructor
public class IngredientIndex {

    private int id;

    private String image;

    private String name;

    private int price;

    private int min;

    private String unit;

    private Integer cartId;

    private Integer cartedAmount;

    private int storeIngredientId;

    public IngredientIndex(Ingredient ingredient) {

        this(
                ingredient.id,
                ingredient.photo,
                ingredient.name,
                ingredient.storeIngredient.get(0).price,
                ingredient.storeIngredient.get(0).min,
                ingredient.unit,
                ingredient.cart == null ? null : ingredient.cart.id,
                ingredient.cart == null ? null : ingredient.cart.amount,
                ingredient.storeIngredient.get(0).id
        );
    }

    public IngredientIndex(IngredientDetailResponse.Ingredient ingredient) {

        this(
                ingredient.id,
                ingredient.photo,
                ingredient.name,
                ingredient.price,
                ingredient.min,
                ingredient.unit,
                ingredient.cart == null ? null : ingredient.cart.id,
                ingredient.cart == null ? null : ingredient.cart.amount,
                0
        );
    }

    public boolean isCarted() {
        return cartId != null && cartedAmount != null && cartedAmount > 0;
    }

    public String getImageUrl() {

        return getIngredientImageUrl(getImage());
    }


}
