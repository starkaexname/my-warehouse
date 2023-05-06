package info.riabokon.mywarehouse.service;

import info.riabokon.mywarehouse.util.TimeRangeException;
import info.riabokon.mywarehouse.model.MaintenanceJob;
import info.riabokon.mywarehouse.repo.MaintenanceJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class MaintenanceJobService {
    @Autowired
    private MaintenanceJobRepository repository;
    public List<MaintenanceJob> getMaintenanceJobs(LocalDateTime startTime, LocalDateTime endTime, Long operatorId, Long areaId) {
        if (startTime == null) startTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.ofHours(0));
        if (endTime == null) endTime = startTime.plusYears(1000);
        if (endTime.isBefore(startTime)){
            throw new TimeRangeException("endTime is earlier than startTime");
        }
        if (operatorId == null && areaId == null) {
            return repository.findAll(startTime, endTime);
        } else if (operatorId == null) {
            return repository.findAllByArea(startTime, endTime, areaId);
        } else if (areaId == null) {
            return repository.findAllByOperator(startTime, endTime, operatorId);
        } else {
            return repository.findAllByOperatorAndArea(startTime, endTime, areaId, operatorId);
        }
    }
}
