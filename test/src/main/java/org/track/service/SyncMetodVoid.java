package org.track.service;

import org.springframework.stereotype.Component;
import org.track.annotation.TrackTime;
import org.track.utils.RandomText;
import org.track.utils.ThreadUtils;

import java.util.Random;

@Component
public class SyncMetodVoid {

    private final Random r = new Random();

    @TrackTime
    public void metodVoid(int value) {
        System.out.println("SyncMetodVoid metodVoid");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
    }

    @TrackTime
    public void metodVoidThrow(int value) {
        System.out.println("SyncMetodVoid metodVoidThrow");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        if (value == 50) {
            throw new RuntimeException("Ошибка RuntimeException в классе SyncMetodVoid в методе metodVoidThrow");
        }
    }
}
