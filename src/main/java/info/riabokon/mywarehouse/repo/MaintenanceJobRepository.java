package info.riabokon.mywarehouse.repo;

import info.riabokon.mywarehouse.model.MaintenanceJob;
import info.riabokon.mywarehouse.model.StatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceJobRepository extends JpaRepository<MaintenanceJob, Long>{
    @Query("SELECT new info.riabokon.mywarehouse.model.StatusCount(mj.status, count(*)) FROM MaintenanceJob mj GROUP BY mj.status")
    List<StatusCount> getStatusCount();
    @Query("SELECT mj FROM MaintenanceJob mj WHERE (mj.endTime > :start_time OR mj.endTime IS NULL) AND  (mj.startTime < :end_time OR mj.startTime IS NULL) order by mj.status")
    List<MaintenanceJob> findAll(@Param("start_time") LocalDateTime startTime, @Param("end_time") LocalDateTime endTime);
    @Query("SELECT mj FROM MaintenanceJob mj WHERE (mj.endTime > :start_time OR mj.endTime IS NULL) AND  (mj.startTime < :end_time OR mj.startTime IS NULL) AND mj.assignedOperator.id = :operator_id order by mj.status")
    List<MaintenanceJob> findAllByOperator(@Param("start_time")LocalDateTime startTime, @Param("end_time") LocalDateTime endTime, @Param("operator_id") Long assignedOperatorId);
    @Query("SELECT mj FROM MaintenanceJob mj WHERE (mj.endTime > :start_time OR mj.endTime IS NULL) AND  (mj.startTime < :end_time OR mj.startTime IS NULL) AND mj.area.id = :area_id order by mj.status")
    List<MaintenanceJob> findAllByArea(@Param("start_time") LocalDateTime startTime, @Param("end_time") LocalDateTime endTime, @Param("area_id") Long areaId);
    @Query("SELECT mj FROM MaintenanceJob mj WHERE (mj.endTime > :start_time OR mj.endTime IS NULL) AND  (mj.startTime < :end_time OR mj.startTime IS NULL) AND mj.assignedOperator.id = :operator_id AND mj.area.id = :area_id order by mj.status")
    List<MaintenanceJob> findAllByOperatorAndArea(@Param("start_time") LocalDateTime startTime, @Param("end_time") LocalDateTime endTime, @Param("area_id") Long areaId, @Param("operator_id") Long assignedOperatorId);
}
