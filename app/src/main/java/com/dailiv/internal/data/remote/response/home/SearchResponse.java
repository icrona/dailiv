package com.dailiv.internal.data.remote.response.home;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/14/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    @JsonProperty(value = "_index")
    public String index;

    @JsonProperty(value = "_type")
    public String type;

    @JsonProperty(value = "_id")
    public String id;

    @JsonProperty(value = "_score")
    public String score;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Source{

        public String name;

        public String slug;

        public String photo;

        public String unit;
    }
}
