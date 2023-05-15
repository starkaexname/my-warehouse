package info.riabokon.mywarehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class WarehouseArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "assignedArea", cascade = CascadeType.ALL)
    private List<Operator> operators;
    @JsonIgnore
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<MaintenanceJob> maintenanceJobs;
}
