package com.dailiv.view.profile.mealplan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dailiv.R;

import lombok.Getter;

/**
 * Created by aldo on 5/27/18.
 */

@Getter
public class MealPlanAdapterViewHolder extends RecyclerView.ViewHolder{

    private TextView tvDay;

    private TextView tvBreakfast;

    private TextView tvLunch;

    private TextView tvDinner;

    private TextView tvSnack;

    public MealPlanAdapterViewHolder(View itemView) {
        super(itemView);
        tvDay = itemView.findViewById(R.id.tv_meal_plan_day);
        tvBreakfast = itemView.findViewById(R.id.tv_meal_plan_breakfast);
        tvLunch = itemView.findViewById(R.id.tv_meal_plan_lunch);
        tvDinner = itemView.findViewById(R.id.tv_meal_plan_dinner);
        tvSnack = itemView.findViewById(R.id.tv_meal_plan_snack);
    }
}
