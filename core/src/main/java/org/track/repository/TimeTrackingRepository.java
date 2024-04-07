package org.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.track.entity.TimeTracking;

import java.util.List;

public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long> {

    List<TimeTracking> findAllByNameMethod(String nameMethod);

    List<TimeTracking> findAllByNameClass(String nameClass);

    @Query("SELECT avg (tt.time) from TimeTracking as tt where tt.nameMethod =?1 and tt.status = 'DONE'")
    Long avgTimeTrackingTimeByNameMethod(String nameMethod);

    @Query("SELECT sum (tt.time) from TimeTracking as tt where tt.nameMethod =?1 and tt.status = 'DONE'")
    Long sumTimeTrackingTimeByNameMethod(String nameMethod);

    @Query("SELECT avg (tt.time) from TimeTracking as tt where tt.nameClass =?1 and tt.status = 'DONE'")
    Long avgTimeTrackingTimeByNameClass(String nameMethod);

    @Query("SELECT sum (tt.time) from TimeTracking as tt where tt.nameClass =?1 and tt.status = 'DONE'")
    Long sumTimeTrackingTimeByNameClass(String nameMethod);


}
