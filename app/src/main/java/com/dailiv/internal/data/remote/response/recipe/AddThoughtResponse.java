package com.dailiv.internal.data.remote.response.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 5/6/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddThoughtResponse {

    public String name;

    public String date;

    public String thought;

    public String photo;

    public String slug;

    @JsonProperty(value = "thought_id")
    public int thoughtId;


//    {
//        "name": "osel",
//            "date": "1 second ago",
//            "thought": "love this recipe a",
//            "photo": "profile-photo/default-avatar.png",
//            "slug": "osel-wang-123121212",
//            "thought_id": 3
//    }
}
