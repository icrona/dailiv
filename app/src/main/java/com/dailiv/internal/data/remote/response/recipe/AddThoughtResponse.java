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

    @JsonProperty(value = "user_id")
    public int userId;


//    {
//        "name": "Osel Wang",
//            "date": "1 second ago",
//            "thought": "love this recipe",
//            "photo": "profile-photo/34/IMG-20170811-WA0000-osel-wang-123121212.jpg",
//            "slug": "osel-wang-123121212",
//            "thought_id": 18,
//            "user_id": 34
//    }
}
