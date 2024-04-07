package org.track.service;

import org.springframework.stereotype.Component;
import org.track.annotation.TrackAsyncTime;
import org.track.utils.RandomText;
import org.track.utils.ThreadUtils;

import java.util.Random;

@Component
public class AsyncMetodVoid {

    private final Random r = new Random();

    @TrackAsyncTime
    public void metodVoid(int value) {
        System.out.println("AsyncMetodVoid metodVoid");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
    }

    @TrackAsyncTime
    public void metodVoidThrow(int value) {
        System.out.println("AsyncMetodVoid metodVoidThrow");
        ThreadUtils.waitTime(value);
        String s = RandomText.getText();
        if (value == 50) {
            throw new RuntimeException("Ошибка RuntimeException в классе AsyncMetodVoid в методе metodVoidThrow");
        }
    }
}
