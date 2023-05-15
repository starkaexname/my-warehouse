package info.riabokon.mywarehouse.controller;

import info.riabokon.mywarehouse.model.WarehouseArea;
import info.riabokon.mywarehouse.service.WarehouseAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/warehouse-area")
public class WarehouseAreaController {
    @Autowired
    private WarehouseAreaService service;
    @GetMapping("/{warehouseAreaId}")
    public WarehouseArea get(@PathVariable Long warehouseAreaId) {
        return service.getWarehouseAreaById(warehouseAreaId);
    }

    @PostMapping
    public ResponseEntity<WarehouseArea> save(@RequestBody WarehouseArea warehouseArea) {
        return new ResponseEntity<>(service.save(warehouseArea), HttpStatus.CREATED);
    }

    @PutMapping("/{warehouseAreaId}")
    public ResponseEntity<WarehouseArea> update(@PathVariable Long warehouseAreaId, @RequestBody WarehouseArea warehouseArea) {
        return new ResponseEntity<>(service.update(warehouseAreaId, warehouseArea), HttpStatus.OK);
    }

    @DeleteMapping("/{warehouseAreaId}")
    public ResponseEntity<Void> delete(@PathVariable Long warehouseAreaId) {
         service.delete(warehouseAreaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
