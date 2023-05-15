package info.riabokon.mywarehouse.service;

import info.riabokon.mywarehouse.model.Operator;
import info.riabokon.mywarehouse.model.WarehouseArea;
import info.riabokon.mywarehouse.repo.OperatorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository repository;
    @Autowired
    private WarehouseAreaService warehouseAreaService;

    public Operator get(Long operatorId){
        return repository.findById(operatorId).orElseThrow(() -> new EntityNotFoundException("Operator with ID=" + operatorId + " not found."));
    }

    public Operator save(Long assignedAreaId, Operator operator) {
        WarehouseArea warehouseAreaFromDB = warehouseAreaService.getWarehouseAreaById(assignedAreaId);
        operator.setAssignedArea(warehouseAreaFromDB);
        return repository.save(operator);
    }

    public Operator update(Long operatorId, Operator operator) {
        Operator operatorFromDB = get(operatorId);
        operatorFromDB.setName(operator.getName());
        return repository.save(operatorFromDB);
    }
    public void delete(Long operatorId){
        repository.deleteById(operatorId);
    }
}
