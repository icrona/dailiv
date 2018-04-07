package com.dailiv.internal.data.local.pojo;

import com.dailiv.BuildConfig;
import com.dailiv.internal.data.remote.response.ingredient.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    public IngredientIndex(Ingredient ingredient) {

        this(
                ingredient.id,
                ingredient.photo,
                ingredient.name,
                ingredient.storeIngredient.get(0).price,
                ingredient.storeIngredient.get(0).min,
                ingredient.unit
        );
    }

    public String getImageUrl() {

        return BuildConfig.ASSET_PREFIX + getImage();
    }


}
