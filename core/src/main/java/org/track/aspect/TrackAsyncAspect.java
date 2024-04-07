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
            Object result = null;
            try {
                Long startTime = System.currentTimeMillis();
                result = proceedingJoinPoint.proceed();
                Long endTime = System.currentTimeMillis();
                timeTracking.setTime(endTime - startTime);
                timeTracking.setStatus(Status.DONE);
            } catch (Throwable e) {
                log.error("Ошибка AsyncRunnerAspect", e);
                timeTracking.setStatus(Status.ERROR);
            }
            return result;
        }).thenApply(result -> {
            timeTrackingService.saveTimeTracking(timeTracking);
            return result;
        }).exceptionally(exception -> {
            log.error("aroundTrackAsyncTime threat  {}", Thread.currentThread().getName());
            return exception.getMessage();
        });
        return future.get();





/*


        CompletableFuture<Object> resultObject=CompletableFuture.supplyAsync(() -> {
            Object result=null;
            try {
                Long startTime = System.currentTimeMillis();
                result = proceedingJoinPoint.proceed();
                //proceedingJoinPoint.proceed();
                log.info("aroundTrackAsyncTime class  {}  name  {} threat  {}",proceedingJoinPoint.getSignature().getDeclaringTypeName(),proceedingJoinPoint.getSignature().getName(), Thread.currentThread().getName());
                Long endTime = System.currentTimeMillis();
                timeTracking.setTime(endTime - startTime);
                timeTracking.setStatus(Status.DONE);
                //  timeTrackingService.saveTimeTracking(timeTracking);
            } catch (Throwable throwable) {
                timeTracking.setStatus(Status.ERROR);
                log.error("aroundTrackAsyncTime throwable  {} threat  {}",throwable.getMessage(), Thread.currentThread().getName());
              //  throw new RuntimeException("Ошибка в методе aroundTrackTime: " + proceedingJoinPoint.getSignature().getName() +
              //          "  " + throwable.getMessage()+"    threat  "+Thread.currentThread());
                result=throwable;
               //  throw new RuntimeException("Ошибка в методе aroundTrackAsyncTime: " + proceedingJoinPoint.getSignature().getName());
            } finally {
                timeTrackingService.saveTimeTracking(timeTracking);
            }
            return result;
        }).exceptionally();
        return resultObject.get();*/
    }

/*        @Around("callAtTrackAsyncTime()")
    public Object aroundTrackAsyncTime(ProceedingJoinPoint proceedingJoinPoint) throws InterruptedException, ExecutionException {
        TimeTracking timeTracking = TimeTracking.builder()
                .nameMethod(proceedingJoinPoint.getSignature().getName())
                .nameClass(proceedingJoinPoint.getSignature().getDeclaringTypeName())
                .dateTime(LocalDateTime.now())
                .build();
        CompletableFuture<Object> completableFuture=CompletableFuture.supplyAsync(() -> {
            try {
                Long startTime = System.currentTimeMillis();
                Object result = proceedingJoinPoint.proceed();
                //proceedingJoinPoint.proceed();
                log.info("aroundTrackAsyncTime class  {}  name  {} threat  {}",proceedingJoinPoint.getSignature().getDeclaringTypeName(),proceedingJoinPoint.getSignature().getName(), Thread.currentThread().getName());
                Long endTime = System.currentTimeMillis();
                timeTracking.setTime(endTime - startTime);
                timeTracking.setStatus(Status.DONE);
                  return result;
            } catch (Throwable throwable) {
                timeTracking.setStatus(Status.ERROR);
                log.error("aroundTrackAsyncTime throwable  {} threat  {}",throwable.getMessage(), Thread.currentThread().getName());
                 return throwable;
               // throw new RuntimeException("Ошибка в методе aroundTrackAsyncTime: " + proceedingJoinPoint.getSignature().getName());
            } finally {
                timeTrackingService.saveTimeTracking(timeTracking);
            }
        });
        return completableFuture.get();
    }*/
}
