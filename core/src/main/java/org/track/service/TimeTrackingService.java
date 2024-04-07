package org.track.service;

import org.track.entity.TimeTracking;
import org.track.model.TypeSearch;

import java.util.List;

public interface TimeTrackingService {

    TimeTracking saveTimeTracking(TimeTracking timeTracking);

    List<TimeTracking> getTimeTrackingNameMethod(String nameMethod);

    List<TimeTracking> getTimeTrackingNameClass(String nameClass);

    Long getAvgTimeTrackingTimeByNameMethod(String nameMethod);

    Long getsumTimeTrackingTimeByNameMethod(String nameMethod);

    Long getTimeTrackingClassСalculation(String nameClass, TypeSearch typeSearch);

}
