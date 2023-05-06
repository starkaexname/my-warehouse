package info.riabokon.mywarehouse.controller;

import info.riabokon.mywarehouse.model.Statistic;
import info.riabokon.mywarehouse.service.StatisticService;
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
@RequestMapping("/statistic")
public class StatisticsController {
    @Autowired
    private StatisticService service;
    @GetMapping
    public List<Statistic> getStatistic(
            @RequestParam("start")
            @DateTimeFormat(pattern = "HH:mm-dd/MM/yyyy")
            LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(pattern = "HH:mm-dd/MM/yyyy")
            LocalDateTime end){
            log.info("start is {}, end is {}", start, end);
        return service.getStatistic(start, end);
    }
}
