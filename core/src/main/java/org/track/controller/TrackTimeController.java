package org.track.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.track.dto.TimeTrackingResponse;
import org.track.entity.TimeTracking;
import org.track.mapper.TimeTrackingMapper;
import org.track.model.TypeSearch;
import org.track.service.TimeTrackingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/track-time")
public class TrackTimeController {

    private final TimeTrackingService timeTrackingService;

    @Operation(summary = "Поиск всех записей по названию метода")
    @GetMapping(value = "/method-name")
    public List<TimeTrackingResponse> getTimeTracking(@RequestParam @Valid @NotNull @NotBlank String nameMethod) {
        log.info("TrackTimeController getEventPub nameMethod {}", nameMethod);
        List<TimeTracking> timeTrackings = timeTrackingService.getTimeTrackingNameMethod(nameMethod);
        return TimeTrackingMapper.toTimeTrackingResponseList(timeTrackings);
    }

    @Operation(summary = "Поиск значения в мс среднего времени выполнения метода по названию")
    @GetMapping(value = "/method-count")
    public Long getTimeTrackingAvg(@RequestParam @Valid @NotNull @NotBlank String nameMethod) {
        log.info("TrackTimeController getTimeTrackingCount nameMethod {}", nameMethod);
        return timeTrackingService.getAvgTimeTrackingTimeByNameMethod(nameMethod);
    }

    @Operation(summary = "Поиск значения в мс суммы всех значений выполнения метода по названию")
    @GetMapping(value = "/method-sum")
    public Long getTimeTrackingSum(@RequestParam @Valid @NotNull @NotBlank String nameMethod) {
        log.info("TrackTimeController getTimeTrackingSum nameMethod {}", nameMethod);
        return timeTrackingService.getsumTimeTrackingTimeByNameMethod(nameMethod);
    }

    @Operation(summary = "Поиск всех записей с разными методами по названию класса")
    @GetMapping(value = "/method-class-name")
    public List<TimeTrackingResponse> getTimeTrackingClass(@RequestParam @Valid @NotNull @NotBlank String nameClass) {
        log.info("TrackTimeController getTimeTrackingClass name {}", nameClass);
        List<TimeTracking> timeTrackings = timeTrackingService.getTimeTrackingNameClass(nameClass);
        return TimeTrackingMapper.toTimeTrackingResponseList(timeTrackings);
    }

    @Operation(summary = "Поиск значения в мс среднего времени или общего времени выполнения методов класса по названию. Результат зависит от выбранного параметра, по умолчанию среднее время")
    @GetMapping(value = "/method-class-type")
    public Long getTimeTrackingClassСalculation(@RequestParam @Valid @NotNull @NotBlank String nameClass,
                                                @RequestParam(name = "courseType", defaultValue = "AVG") TypeSearch typeSearch) {
        log.info("TrackTimeController getTimeTrackingClass name {} typeSearch {}", nameClass, typeSearch);
        return timeTrackingService.getTimeTrackingClassСalculation(nameClass, typeSearch);
    }
}
