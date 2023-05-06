package info.riabokon.mywarehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Statistic {
    @Id
    private LocalDateTime time=LocalDateTime.now();
    @PositiveOrZero
    private Integer open = 0;
    private Integer diffOpen = 0;
    @PositiveOrZero
    private Integer inProgress = 0;
    private Integer diffInProgress = 0;
    @PositiveOrZero
    private Integer finished = 0;
    private Integer diffFinished = 0;
}
