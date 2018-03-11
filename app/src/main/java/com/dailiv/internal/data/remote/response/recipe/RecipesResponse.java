package com.dailiv.internal.data.remote.response.recipe;

import com.dailiv.internal.data.remote.response.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipesResponse extends Pagination {

    public List<Recipe> data;

}
