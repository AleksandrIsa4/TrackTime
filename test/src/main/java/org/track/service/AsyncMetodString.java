package org.track.service;

import org.springframework.stereotype.Component;
import org.track.annotation.TrackAsyncTime;
import org.track.utils.RandomText;
import org.track.utils.ThreadUtils;

import java.util.Random;

@Component
public class AsyncMetodString {

    private final Random r = new Random();

    @TrackAsyncTime
    public String metodStringThrow(int value) {
        System.out.println("AsyncMetodString metodStringThrow");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        if (value == 50) {
            throw new RuntimeException("Ошибка RuntimeException в классе AsyncMetodString в методе metodStringThrow");
        }
        return s;
    }

    @TrackAsyncTime
    public String metodString(int value) {
        System.out.println("AsyncMetodString metodString");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        return s;
    }
}
