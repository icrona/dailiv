package com.dailiv.internal.data.local.pojo;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 4/22/18.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanning {

    private int recipeId;

    private PlanningCategory planningCategory;

    private LocalDate planningDate;

    public String getPlanningDateString() {
        return getPlanningDate().toString(
                DateTimeFormat.forPattern("yyyy-MM-dd"));
    }

    public boolean isValid() {
        return getPlanningCategory() != null && getPlanningDate() != null;
    }

}
