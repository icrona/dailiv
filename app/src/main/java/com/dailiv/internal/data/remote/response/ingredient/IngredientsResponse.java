package com.dailiv.internal.data.remote.response.ingredient;

import com.dailiv.internal.data.remote.response.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientsResponse extends Pagination {

    public List<Ingredient> data;
}
