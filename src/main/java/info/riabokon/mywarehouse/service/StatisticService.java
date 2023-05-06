package info.riabokon.mywarehouse.service;

import info.riabokon.mywarehouse.util.TimeRangeException;
import info.riabokon.mywarehouse.model.Statistic;
import info.riabokon.mywarehouse.model.Status;
import info.riabokon.mywarehouse.model.StatusCount;
import info.riabokon.mywarehouse.repo.MaintenanceJobRepository;
import info.riabokon.mywarehouse.repo.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private MaintenanceJobRepository maintenanceJobRepository;
    @Scheduled(cron = "0 0 * * * ?")
    public void calculateStatistic () {
        Statistic currentStatistic = generateCurrentStatistic(maintenanceJobRepository.getStatusCount());
        Statistic previosStatistic = statisticRepository.getPreviosStatistic();
        Statistic newStatistic = previosStatistic != null ? calculateDiffs(currentStatistic, previosStatistic) : setInitialDiffs(currentStatistic);
        saveNewStatistic(newStatistic);
    }

    private void saveNewStatistic(Statistic newStatistic) {
        statisticRepository.save(newStatistic);
    }

    private Statistic calculateDiffs(Statistic currentStatistic, Statistic previosStatistic) {
        currentStatistic.setDiffOpen(currentStatistic.getOpen() - previosStatistic.getOpen());
        currentStatistic.setDiffInProgress(currentStatistic.getInProgress() - previosStatistic.getInProgress());
        currentStatistic.setDiffFinished(currentStatistic.getFinished() - previosStatistic.getFinished());
        return currentStatistic;
    }

    private Statistic generateCurrentStatistic(List<StatusCount> statusCounts) {
        Statistic currentStatistic = new Statistic();
        statusCounts.forEach(statusCount -> {
            if (statusCount.getStatus() == Status.OPEN){
                currentStatistic.setOpen(statusCount.getCount().intValue());
            } else if (statusCount.getStatus() == Status.FINISHED) {
                currentStatistic.setFinished(statusCount.getCount().intValue());
            } else if (statusCount.getStatus() == Status.IN_PROGRESS) {
                currentStatistic.setInProgress(statusCount.getCount().intValue());
            }
            else throw new RuntimeException("Unknown status");
        });
        return currentStatistic;
    }

    public List<Statistic> getStatistic(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)){
            throw new TimeRangeException("endTime is earlier than startTime");
        }
        return statisticRepository.findAllByTimeBetweenOrderByTimeDesc(start, end);
    }

    public Statistic setInitialDiffs(Statistic currentStatistic){
        currentStatistic.setDiffOpen(currentStatistic.getOpen());
        currentStatistic.setDiffInProgress(currentStatistic.getInProgress());
        currentStatistic.setDiffFinished(currentStatistic.getFinished());
        return currentStatistic;
    }
}
