package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 4/22/18.
 */

@Getter
@AllArgsConstructor
public enum PlanningCategory {

    NONE("Select Meal Time", null),
    BREAKFAST("Breakfast", "Breakfast"),
    LUNCH("Lunch", "Lunch"),
    DINNER("Dinner", "Dinner"),
    SNACK("Snack", "Snack");

    private String text;

    private String key;
}
