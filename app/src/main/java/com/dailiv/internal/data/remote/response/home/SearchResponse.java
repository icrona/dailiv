package com.dailiv.internal.data.remote.response.home;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by aldo on 3/14/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    public List<Search> recipe;

    public List<Search> ingredient;

}

