package com.dailiv.internal.data.local.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 4/8/18.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientFilter {

    private List<String> category = new ArrayList<>();

    private Integer fromPrice = 500;

    private Integer toPrice = 200000;

    private Integer page;

    public void resetPrice(){

        setFromPrice(500);
        setToPrice(200000);
    }

    public void resetCategory() {

        category.clear();
    }

}
