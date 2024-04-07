package org.track.service;

import org.springframework.stereotype.Component;
import org.track.annotation.TrackTime;
import org.track.utils.RandomText;
import org.track.utils.ThreadUtils;

import java.util.Random;

@Component
public class SyncMetodString {

    private final Random r = new Random();

    @TrackTime
    public String metodStringThrow(int value) {
        System.out.println("SyncMetodString metodStringThrow");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        if (value == 50) {
            throw new RuntimeException("Ошибка RuntimeException в классе SyncMetodString в методе metodStringThrow");
        }
        return s;
    }

    @TrackTime
    public String metodString(int value) {
        System.out.println("SyncMetodString metodString");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        return s;
    }
}
