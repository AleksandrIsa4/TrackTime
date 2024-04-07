package org.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.track.entity.TimeTracking;
import org.track.exceptions.DataNotFoundException;
import org.track.model.TypeSearch;
import org.track.repository.TimeTrackingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTrackingServiceImpl implements TimeTrackingService {

    private final TimeTrackingRepository timeTrackingRepository;

    private void checkNotEmptyList(List<TimeTracking> timeTrackings, String nameMethod, String nameClass) {
        if (nameMethod != null && timeTrackings.size() == 0) {
            throw new DataNotFoundException("Нет метода с именем " + nameMethod);
        } else if (nameClass != null && timeTrackings.size() == 0) {
            throw new DataNotFoundException("Нет класса с именем " + nameClass);
        }
    }

    private void checkNotEmptyNumber(Long number, String nameMethod, String nameClass) {
        if (nameMethod != null && number == null) {
            throw new DataNotFoundException("Нет метода с именем " + nameMethod);
        } else if (nameClass != null && number == null) {
            throw new DataNotFoundException("Нет класса с именем " + nameClass);
        }
    }

    @Transactional
    public TimeTracking saveTimeTracking(TimeTracking timeTracking) {
        return timeTrackingRepository.save(timeTracking);
    }

    @Transactional(readOnly = true)
    public List<TimeTracking> getTimeTrackingNameMethod(String nameMethod) {
        List<TimeTracking> timeTrackings = timeTrackingRepository.findAllByNameMethod(nameMethod);
        checkNotEmptyList(timeTrackings, nameMethod, null);
        return timeTrackings;
    }

    @Transactional(readOnly = true)
    public List<TimeTracking> getTimeTrackingNameClass(String nameClass) {
        List<TimeTracking> timeTrackings = timeTrackingRepository.findAllByNameClass(nameClass);
        checkNotEmptyList(timeTrackings, null, nameClass);
        return timeTrackings;
    }

    @Transactional(readOnly = true)
    public Long getAvgTimeTrackingTimeByNameMethod(String nameMethod) {
        Long avg = timeTrackingRepository.avgTimeTrackingTimeByNameMethod(nameMethod);
        checkNotEmptyNumber(avg, nameMethod, null);
        return avg;
    }

    @Transactional(readOnly = true)
    public Long getsumTimeTrackingTimeByNameMethod(String nameMethod) {
        Long sum = timeTrackingRepository.sumTimeTrackingTimeByNameMethod(nameMethod);
        checkNotEmptyNumber(sum, nameMethod, null);
        return sum;
    }

    @Transactional(readOnly = true)
    public Long getTimeTrackingClassСalculation(String nameClass, TypeSearch typeSearch) {
        Long value = null;
        switch (typeSearch) {
            case AVG -> {
                value = timeTrackingRepository.avgTimeTrackingTimeByNameClass(nameClass);
            }
            case SUM -> {
                value = timeTrackingRepository.sumTimeTrackingTimeByNameClass(nameClass);
            }
        }
        checkNotEmptyNumber(value, null, nameClass);
        return value;
    }
}
