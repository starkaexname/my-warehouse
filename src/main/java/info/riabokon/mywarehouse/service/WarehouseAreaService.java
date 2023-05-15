package info.riabokon.mywarehouse.service;

import info.riabokon.mywarehouse.model.WarehouseArea;
import info.riabokon.mywarehouse.repo.WarehouseAreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseAreaService {
    @Autowired
    private WarehouseAreaRepository repository;

    public WarehouseArea getWarehouseAreaById(Long warehouseAreaId){
        return repository.findById(warehouseAreaId).orElseThrow(() -> new EntityNotFoundException("Area with ID=" + warehouseAreaId + " not found."));
    }
    public WarehouseArea save(WarehouseArea warehouseArea) {
        return repository.save(warehouseArea);
    }

    public WarehouseArea update(Long warehouseAreaId, WarehouseArea warehouseArea) {
        WarehouseArea warehouseAreaFromDB = getWarehouseAreaById(warehouseAreaId);
        warehouseAreaFromDB.setName(warehouseArea.getName());
        return repository.save(warehouseAreaFromDB);
    }

    public void delete(Long warehouseAreaId){
        repository.deleteById(warehouseAreaId);
    }
}
