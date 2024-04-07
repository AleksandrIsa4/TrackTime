package org.track.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.track.dto.TimeTrackingResponse;
import org.track.entity.TimeTracking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeTrackingMapper {

    public static TimeTrackingResponse toTimeTrackingResponse(TimeTracking timeTracking) {
        return TimeTrackingResponse.builder()
                .time(timeTracking.getTime())
                .dateTime(timeTracking.getDateTime())
                .status(timeTracking.getStatus())
                .nameMethod(timeTracking.getNameMethod())
                .nameClass(timeTracking.getNameClass())
                .build();
    }

    public static List<TimeTrackingResponse> toTimeTrackingResponseList(List<TimeTracking> timeTrackings) {
        if (timeTrackings == null) {
            return null;
        } else {
            List<TimeTrackingResponse> list = new ArrayList(timeTrackings.size());
            Iterator var3 = timeTrackings.iterator();
            while (var3.hasNext()) {
                TimeTracking timeTracking = (TimeTracking) var3.next();
                list.add(TimeTrackingMapper.toTimeTrackingResponse(timeTracking));
            }
            return list;
        }
    }
}
