package com.dailiv.internal.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {

    public int total;

    @JsonProperty(value = "per_page")
    public int perPage;

    @JsonProperty(value = "current_page")
    public int currentPage;

    @JsonProperty(value = "last_page")
    public int lastPage;

    @JsonProperty(value = "next_page_url")
    public String nextPageUrl;

    @JsonProperty(value = "prev_page_url")
    public String prevPageUrl;

    public int from;

    public int to;
}
