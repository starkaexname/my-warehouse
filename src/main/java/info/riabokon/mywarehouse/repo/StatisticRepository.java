package info.riabokon.mywarehouse.repo;

import info.riabokon.mywarehouse.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, LocalDateTime> {
    @Query("SELECT st FROM Statistic st ORDER BY st.time DESC LIMIT 1")
    Statistic getPreviosStatistic();
    List<Statistic> findAllByTimeBetweenOrderByTimeDesc(LocalDateTime start, LocalDateTime end);
}
