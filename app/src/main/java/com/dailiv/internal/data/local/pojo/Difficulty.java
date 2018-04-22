package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 4/21/18.
 */

@Getter
@AllArgsConstructor
public enum Difficulty {

    EASY("Easy", "easy"),
    MEDIUM("Medium", "medium"),
    HARD("Hard", "hard");

    private String text;
    private String key;
}
