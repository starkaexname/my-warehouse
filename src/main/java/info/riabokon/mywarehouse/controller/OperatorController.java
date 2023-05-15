package info.riabokon.mywarehouse.controller;

import info.riabokon.mywarehouse.model.Operator;
import info.riabokon.mywarehouse.service.OperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/operator")
public class OperatorController {
    @Autowired
    public OperatorService service;

    @GetMapping("/{operatorId}")
    public Operator get(@PathVariable Long operatorId) {
        return service.get(operatorId);
    }

    @PostMapping
    public ResponseEntity<Operator> save(@RequestParam Long assignedAreaId, @RequestBody Operator operator) {
        return new ResponseEntity<>(service.save(assignedAreaId, operator), HttpStatus.CREATED);
    }

    @PutMapping("/{operatorId}")
    public ResponseEntity<Operator> update(@PathVariable Long operatorId, @RequestBody Operator operator) {
        return new ResponseEntity<>(service.update(operatorId, operator), HttpStatus.OK);
    }

    @DeleteMapping("/{operatorId}")
    public ResponseEntity<Void> delete(@PathVariable Long operatorId) {
        service.delete(operatorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
