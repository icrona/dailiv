package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.home.SearchResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by aldo on 3/31/18.
 */

@Data
@AllArgsConstructor
public class SearchResult {

    private SearchType searchType;

    private String name;

    private String identifier;

    public SearchResult(SearchResponse response) {
        this(
                response.type.equals("recipe") ? SearchType.RECIPE : SearchType.INGREDIENT,
                response.source.name,
                response.type.equals("recipe") ? response.source.slug : response.id
        );
    }
}
