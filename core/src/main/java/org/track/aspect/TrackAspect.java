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
            log.info("aroundTrackTime class  {}  name  {} threat  {}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName(), Thread.currentThread().getName());
            Long endTime = System.currentTimeMillis();
            timeTracking.setTime(endTime - startTime);
            timeTracking.setStatus(Status.DONE);
        } catch (Throwable throwable) {
            timeTracking.setStatus(Status.ERROR);
            log.error("aroundTrackTime throwable  {} threat  {}", throwable.getMessage(), Thread.currentThread().getName());
            result = throwable;
        } finally {
            timeTrackingService.saveTimeTracking(timeTracking);
        }
        return result;
    }
}
