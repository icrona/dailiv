package com.dailiv.util.common;

import java.util.Collection;
import java.util.Map;

/**
 * Created by aldo on 4/6/18.
 */

public class PresentUtil {

    public static <T> boolean isPresent(Collection<T> list) {
        return list != null && !list.isEmpty();
    }

    public static <T, R> boolean isPresent(Map<T, R> map) {
        return map != null && !map.isEmpty();
    }
}
