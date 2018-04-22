package com.dailiv.view.custom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.local.pojo.PlanningCategory;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by aldo on 4/22/18.
 */

public abstract class MealPlanningDialog extends BaseDialog{
    private MealPlanning mealPlanning;

    private TextView tvDate;

    private List<PlanningCategory> planningCategories = Arrays.asList(PlanningCategory.values());

    public MealPlanningDialog(Context context, LayoutInflater layoutInflater) {

        super(context, layoutInflater, R.layout.dialog_meal_planning);
    }

    public void show(int recipeId) {
        mealPlanning = new MealPlanning();
        mealPlanning.setRecipeId(recipeId);

        tvDate = mView.findViewById(R.id.tv_select_date);
        tvDate.setText("Select date");
        AppCompatSpinner spMealCategory = mView.findViewById(R.id.sp_meal_category);

        tvDate.setOnClickListener(view -> {
            final DatePickerDialog datePickerDialog = initDatePickerDialog();
            datePickerDialog.show();
        });

        MealCategoryAdapter mealCategoryAdapter = new MealCategoryAdapter(context, R.layout.item_spinner, planningCategories);
        spMealCategory.setAdapter(mealCategoryAdapter);

        spMealCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0) {
                    return;
                }

                mealPlanning.setPlanningCategory(planningCategories.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnApply.setOnClickListener(view -> {
            dialog.dismiss();
            submitAction().call(mealPlanning);
        });

        dialog.show();
    }

    private DatePickerDialog initDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                context,
                onDateSetListener(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener() {

        return (datePicker, i, i1, i2) -> {
            final Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            mealPlanning.setPlanningDate(LocalDate.fromCalendarFields(calendar));
            tvDate.setText(mealPlanning.getPlanningDateString());
        };
    }

    public abstract Action1<MealPlanning> submitAction();
}
