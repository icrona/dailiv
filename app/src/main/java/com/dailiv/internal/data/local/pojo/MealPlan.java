package com.dailiv.internal.data.local.pojo;

import android.support.v4.util.Pair;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.annimon.stream.IntStream;
import com.annimon.stream.function.Function;
import com.dailiv.internal.data.remote.response.mealplan.MealPlanResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * Created by aldo on 5/29/18.
 */

@Data
public class MealPlan {

    private String day;

    private List<MealPlanRecipe> breakfast;

    private List<MealPlanRecipe> lunch;

    private List<MealPlanRecipe> dinner;

    private List<MealPlanRecipe> snack;

    @Data
    @AllArgsConstructor
    public static class MealPlanRecipe{

        private String slug;

        private String name;
    }

    public String getDay() {
        return capitalize(day);
    }

    public MealPlan(Map.Entry<String, MealPlanResponse> entry) {

        this.day = entry.getKey();
        this.breakfast = mapListToList(entry.getValue().breakfast, this::mapMealRecipe);
        this.lunch = mapListToList(entry.getValue().lunch, this::mapMealRecipe);
        this.dinner = mapListToList(entry.getValue().dinner, this::mapMealRecipe);
        this.snack = mapListToList(entry.getValue().snack, this::mapMealRecipe);
    }

    private MealPlanRecipe mapMealRecipe(MealPlanResponse.MealPlanRecipeResponse recipe) {

        return new MealPlanRecipe(
                recipe.recipe.slug,
                recipe.recipe.name
        );
    }
}
