package com.dailiv.internal.data.local.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 4/21/18.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFilter {

    private List<String> category = new ArrayList<>();

    private Integer fromDuration = 1;

    private Integer toDuration = 400;

    private Integer page;

    private SortBy sortBy = SortBy.NEWEST;

    private Difficulty difficulty;

    public void resetDuration() {

        setFromDuration(1);
        setToDuration(400);
    }

    public void resetCategory() {
        category.clear();
    }

    public void resetDifficulty() {

        setDifficulty(null);
    }
}
