package info.riabokon.mywarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MaintenanceJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id")
    private WarehouseArea area;
    @ManyToOne
    @JoinColumn(name = "assigned_operator_id")
    private Operator assignedOperator;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @AssertTrue(message = "endTime is earlier than startTime")
    private boolean isValidDates() {
        if (endTime == null)
            return true;
        else
            return startTime != null && (endTime.isAfter(startTime) || endTime.isEqual(startTime));
    }
}
