package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }
    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot newSpot = new Spot();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        if(numberOfWheels == 2){
            newSpot.setOccupied(true);
            newSpot.setSpotType(SpotType.TWO_WHEELER);
            newSpot.setPricePerHour(pricePerHour);
        }
        else if(numberOfWheels > 2 && numberOfWheels <= 4){
            newSpot.setOccupied(true);
            newSpot.setSpotType(SpotType.FOUR_WHEELER);
            newSpot.setPricePerHour(pricePerHour);
        }
        else{
            newSpot.setOccupied(true);
            newSpot.setSpotType(SpotType.OTHERS);
            newSpot.setPricePerHour(pricePerHour);
        }

        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(newSpot);
        parkingLot.setSpotList(spotList);

        newSpot.setParkingLot(parkingLot);

        parkingLotRepository1.save(parkingLot);

        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).get();
        List<Spot> spotList = spot.getParkingLot().getSpotList();
        for(Spot spot1: spotList){
            if(spot1.getId() == spotId){
                spotList.remove(spot1);
            }
        }
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot updatedSpot = spotRepository1.findById(spotId).get();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        updatedSpot.setOccupied(false);
        updatedSpot.setPricePerHour(pricePerHour);
        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(updatedSpot);
        parkingLot.setSpotList(spotList);

        updatedSpot.setParkingLot(parkingLot);

        parkingLotRepository1.save(parkingLot);
        return updatedSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
//        List<Spot> spots = parkingLot.getSpotList();
//        for(Spot spot: spots){
//            spots.remove(spot);
//        }
        parkingLotRepository1.delete(parkingLot);
    }
}

//package com.driver.services.impl;
//
//import com.driver.model.ParkingLot;
//import com.driver.model.Spot;
//import com.driver.model.SpotType;
//import com.driver.repository.ParkingLotRepository;
//import com.driver.repository.SpotRepository;
//import com.driver.services.ParkingLotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ParkingLotServiceImpl implements ParkingLotService {
//    @Autowired
//    ParkingLotRepository parkingLotRepository1;
//    @Autowired
//    SpotRepository spotRepository1;
//    @Override
//    public ParkingLot addParkingLot(String name, String address) {
//
//
//    }
//
//    @Override
//    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
//
//    }
//
//    @Override
//    public void deleteSpot(int spotId) {
//
//    }
//
//    @Override
//    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
//
//    }
//
//    @Override
//    public void deleteParkingLot(int parkingLotId) {
//
//    }
//}
