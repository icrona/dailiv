package com.dailiv.util.common;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;

import java.util.List;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.PresentUtil.isPresent;
import static java.util.Collections.emptyList;

/**
 * Created by aldo on 4/6/18.
 */

public class CollectionUtil {

    public static <T, R> List<T> mapListToList(List<R> before, Function<R, T> mapper) {
        if(isPresent(before)) {
            return Stream
                    .of(before)
                    .map(mapper)
                    .collect(toList());

        }
        return emptyList();
    }
}
