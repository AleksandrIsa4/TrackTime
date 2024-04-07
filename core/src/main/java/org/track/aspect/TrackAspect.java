package org.track.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.track.entity.TimeTracking;
import org.track.model.Status;
import org.track.service.TimeTrackingService;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackAspect {

    private final TimeTrackingService timeTrackingService;

    @Pointcut("@annotation(org.track.annotation.TrackTime)")
    public void callAtTrackTime() {
    }

    @Around("callAtTrackTime()")
    public Object aroundTrackTimeVoid(ProceedingJoinPoint proceedingJoinPoint) {
        TimeTracking timeTracking = TimeTracking.builder()
                .nameMethod(proceedingJoinPoint.getSignature().getName())
                .nameClass(proceedingJoinPoint.getSignature().getDeclaringTypeName())
                .dateTime(LocalDateTime.now())
                .build();
        Object result;
        try {
            Long startTime = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            Long endTime = System.currentTimeMillis();
            timeTracking.setTime(endTime - startTime);
            timeTracking.setStatus(Status.DONE);
        } catch (Throwable throwable) {
            timeTracking.setStatus(Status.ERROR);
            log.error("Произошла ошибка при вызове метода: {}", proceedingJoinPoint.getSignature().getName());
            log.error("Ошибка: {}", throwable.getMessage());
            result = throwable;
        } finally {
            CompletableFuture.runAsync(() ->
            timeTrackingService.saveTimeTracking(timeTracking));
        }
        return result;
    }
}
