package info.riabokon.mywarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "assigned_area_id")
    private WarehouseArea assignedArea;
    @OneToMany(mappedBy = "assignedOperator", cascade = CascadeType.ALL)
    private List<MaintenanceJob> maintenanceJobs;
}
