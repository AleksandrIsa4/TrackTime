package org.track;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.track.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MainTestMetodTrack {
    private final AsyncMetodString asyncMetodString;
    private final AsyncMetodVoid asyncMetodVoid;

    private final SyncMetodString syncMetodString;

    private final SyncMetodVoid syncMetodVoid;

    public static void main(String[] args) {
        SpringApplication.run(MainTestMetodTrack.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncMetodString.metodStringThrow(50);
            }
        }).start();
        syncMetodString.metodStringThrow(77);
        syncMetodString.metodString(55);
        syncMetodVoid.metodVoid(55);
        syncMetodVoid.metodVoidThrow(78);
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncMetodVoid.metodVoidThrow(50);
            }
        }).start();
        String s1 = asyncMetodString.metodStringThrow(60);
        String s2 = asyncMetodString.metodStringThrow(50);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s3 = asyncMetodString.metodStringThrow(50);
            }
        }).start();
        String s4 = asyncMetodString.metodString(33);
        asyncMetodVoid.metodVoid(77);
        asyncMetodVoid.metodVoidThrow(50);
    }
}