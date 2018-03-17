package com.dailiv.util.common;

import android.support.v4.util.Pair;

/**
 * Created by aldo on 3/16/18.
 */

public class NameUtil {

    public static Pair<String, String> splitName(String name) {

        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }

        if(lastName.equals("")) {
            lastName = firstName;
        }

        return Pair.create(firstName, lastName);
    }
}
