package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.recipe.Recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by aldo on 4/1/18.
 */

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RecipeOfTheDay extends RecipeIndex{

    private int duration;

    private String desc;

    private String username;

    public RecipeOfTheDay(Recipe recipe) {
        super(recipe);
        this.duration = recipe.duration;
        this.desc = recipe.description;
        this.username = recipe.user.firstname + recipe.user.lastname;

    }
}
