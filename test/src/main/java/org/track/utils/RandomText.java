package org.track.utils;

import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
public class RandomText {

    private static final Random r = new Random();

    public static String getText() {
        int length = 16;
        String s = r.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return s;
    }
}
