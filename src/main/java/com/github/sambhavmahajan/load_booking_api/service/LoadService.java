package com.github.sambhavmahajan.load_booking_api.service;

import com.github.sambhavmahajan.load_booking_api.dto.LoadDTO;
import com.github.sambhavmahajan.load_booking_api.entities.LoadEntity;
import com.github.sambhavmahajan.load_booking_api.entities.LoadStatus;
import com.github.sambhavmahajan.load_booking_api.repo.LoadRepo;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoadService {
    private final LoadRepo loadRepo;
    public LoadService(LoadRepo loadRepo) {
        this.loadRepo = loadRepo;
    }
    public void transferFromDTO(LoadDTO loadDTO, LoadEntity load) {
        load.setShipperId(loadDTO.shipperId);
        load.setFacility(loadDTO.facility);
        load.setProductType(loadDTO.productType);
        load.setTruckType(loadDTO.truckType);
        load.setNoOfTrucks(loadDTO.noOfTrucks);
        load.setWeight(loadDTO.weight);
        load.setComment(loadDTO.comment);
        load.setStatus(LoadStatus.POSTED);
    }
    @Transactional
    public void createLoad(LoadDTO loadDTO) {
        LoadEntity load = new LoadEntity();
        transferFromDTO(loadDTO, load);
        loadRepo.save(load);
        System.out.println("Load created of id " + load.getId());
    }
    public ArrayList<LoadEntity> getLoadsByShipperId(String shipperId) {
        return loadRepo.findByShipperId(shipperId);
    }
    public ArrayList<LoadEntity> getLoadsByTruckType(String truckType) {
        return loadRepo.findByTruckType(truckType);
    }
    public ArrayList<LoadEntity> getLoadsByShipperAndTruck(String shipperId, String truckType) {
        ArrayList<LoadEntity> res = getLoadsByShipperId(shipperId);
        res.retainAll(loadRepo.findByTruckType(truckType));
        return res;
    }
    public LoadEntity getLoadById(UUID id) throws ResponseStatusException{
        Optional<LoadEntity> load = loadRepo.findById(id);
        if(load.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Load entity with id " + id + " not found");
        }
        return load.get();
    }
    @Transactional
    public void updateLoad(UUID id, LoadDTO loadDTO) {
        Optional<LoadEntity> load = loadRepo.findById(id);
        if(load.isEmpty() || !loadRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404) , "No such entity exits!");
        }
        transferFromDTO(loadDTO, load.get());
        load.get().setStatus(loadDTO.status);
        loadRepo.save(load.get());
    }
    @Transactional
    public void deleteLoad(UUID id) {
        if(!loadRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No such entity exits!");
        }
        loadRepo.deleteById(id);
    }
}
