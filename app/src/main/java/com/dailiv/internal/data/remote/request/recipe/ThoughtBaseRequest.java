package com.dailiv.internal.data.remote.request.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

public class ThoughtBaseRequest {

    @JsonProperty(value = "thought_id")
    public int thoughtId;
}
