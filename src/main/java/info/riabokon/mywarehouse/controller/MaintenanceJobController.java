package info.riabokon.mywarehouse.controller;

import info.riabokon.mywarehouse.model.MaintenanceJob;
import info.riabokon.mywarehouse.service.MaintenanceJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/maintenance-job")
public class MaintenanceJobController {
    @Autowired
    private MaintenanceJobService service;
    @GetMapping
    public List<MaintenanceJob> getMaintenanceJobs(
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(pattern = "HH:mm-dd/MM/yyyy")
            LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = "HH:mm-dd/MM/yyyy")
            LocalDateTime endTime,
            @RequestParam(value = "operator", required = false)
            Long operatorId,
            @RequestParam(value = "area", required = false)
            Long areaId) {
        log.info("startTime is {}, endTime is {}, operatorId is {}, areaId is {} ", startTime, endTime, operatorId, areaId);
        return service.getMaintenanceJobs(startTime, endTime, operatorId, areaId);
    }
}
