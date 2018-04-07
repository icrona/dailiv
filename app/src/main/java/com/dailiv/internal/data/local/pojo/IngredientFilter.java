package com.dailiv.internal.data.local.pojo;

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

    private List<String> category;

    private Integer fromPrice;

    private Integer toPrice;

    private Integer page;

}
