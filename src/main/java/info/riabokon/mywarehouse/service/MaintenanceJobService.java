package info.riabokon.mywarehouse.service;

import info.riabokon.mywarehouse.model.Operator;
import info.riabokon.mywarehouse.model.WarehouseArea;
import info.riabokon.mywarehouse.util.TimeRangeException;
import info.riabokon.mywarehouse.model.MaintenanceJob;
import info.riabokon.mywarehouse.repo.MaintenanceJobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class MaintenanceJobService {
    @Autowired
    private MaintenanceJobRepository repository;
    @Autowired
    private WarehouseAreaService warehouseAreaService;
    @Autowired
    private OperatorService operatorService;
    public List<MaintenanceJob> getMaintenanceJobs(LocalDateTime startTime, LocalDateTime endTime, Long operatorId, Long areaId) {
        if (startTime == null) startTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.ofHours(0));
        if (endTime == null) endTime = startTime.plusYears(1000);
        if (endTime.isBefore(startTime)) {
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

    public MaintenanceJob get(Long maintenanceJobId){
        return repository.findById(maintenanceJobId).orElseThrow(() -> new EntityNotFoundException("MaintenanceJob with ID=" + maintenanceJobId + " not found."));
    }
    public MaintenanceJob save(Long operatorId, Long assignedAreaId, MaintenanceJob maintenanceJob) {
        WarehouseArea warehouseAreaFromDB = warehouseAreaService.getWarehouseAreaById(assignedAreaId);
        Operator operatorFromDB = operatorService.get(operatorId);
        maintenanceJob.setArea(warehouseAreaFromDB);
        maintenanceJob.setAssignedOperator(operatorFromDB);
        return repository.save(maintenanceJob);
    }

    public MaintenanceJob update(Long maintenanceJobId, MaintenanceJob maintenanceJob) {
        MaintenanceJob maintenanceJobFromDB = get(maintenanceJobId);
        maintenanceJobFromDB.setStatus(maintenanceJob.getStatus());
        maintenanceJobFromDB.setStartTime(maintenanceJob.getStartTime());
        maintenanceJobFromDB.setEndTime(maintenanceJob.getEndTime());
        return repository.save(maintenanceJobFromDB);
    }
    public void delete(Long maintenanceJobId){
        repository.deleteById(maintenanceJobId);
    }
}
