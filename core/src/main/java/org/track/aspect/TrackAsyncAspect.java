package org.track.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.track.entity.TimeTracking;
import org.track.model.Status;
import org.track.service.TimeTrackingService;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackAsyncAspect {

    private final TimeTrackingService timeTrackingService;

    @Pointcut("@annotation(org.track.annotation.TrackAsyncTime)")
    public void callAtTrackAsyncTime() {
    }

    @Around("callAtTrackAsyncTime()")
    public Object aroundTrackAsyncTime(ProceedingJoinPoint proceedingJoinPoint) throws InterruptedException, ExecutionException {
        TimeTracking timeTracking = TimeTracking.builder()
                .nameMethod(proceedingJoinPoint.getSignature().getName())
                .nameClass(proceedingJoinPoint.getSignature().getDeclaringTypeName())
                .dateTime(LocalDateTime.now())
                .build();
        final CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            Object result=null;
            try {
                Long startTime = System.currentTimeMillis();
                result = proceedingJoinPoint.proceed();
                Long endTime = System.currentTimeMillis();
                timeTracking.setTime(endTime - startTime);
                timeTracking.setStatus(Status.DONE);
            } catch (Throwable e) {
                timeTracking.setStatus(Status.ERROR);
                log.error("Произошла ошибка при вызове метода: {}", proceedingJoinPoint.getSignature().getName());
                log.error("Ошибка: {}", e.getMessage());
            }
            return result;
        }).thenApply(result -> {
            CompletableFuture.runAsync(() ->
                    timeTrackingService.saveTimeTracking(timeTracking));
            return result;
        });
        return future.get();
    }
}