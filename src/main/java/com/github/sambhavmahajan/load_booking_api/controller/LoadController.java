package com.github.sambhavmahajan.load_booking_api.controller;

import com.github.sambhavmahajan.load_booking_api.dto.LoadDTO;
import com.github.sambhavmahajan.load_booking_api.entities.LoadEntity;
import com.github.sambhavmahajan.load_booking_api.service.LoadService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/load")
public class LoadController {
    private final LoadService loadService;
    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }
    @PostMapping
    public void createLoad(@RequestBody LoadDTO loadDTO) {
        loadService.createLoad(loadDTO);
    }
    @GetMapping
    public ArrayList<LoadEntity> fetchLoads(@RequestParam("shipperId") String shipperId, @RequestParam("truckType") String truckType) {
        return loadService.getLoadsByShipperAndTruck(shipperId, truckType);
    }
    @GetMapping("/{loadId}")
    public LoadEntity fetchLoad(@PathVariable("loadId") UUID loadId) {
        return loadService.getLoadById(loadId);
    }
    @PutMapping("/{loadId}")
    public void updateLoad(@PathVariable("loadId") UUID loadId, @RequestBody LoadDTO loadDTO) {
        loadService.updateLoad(loadId, loadDTO);
    }
    @DeleteMapping("/{loadId}")
    public void deleteLoad(@PathVariable("loadId") UUID loadId) {
        loadService.deleteLoad(loadId);
    }
}
