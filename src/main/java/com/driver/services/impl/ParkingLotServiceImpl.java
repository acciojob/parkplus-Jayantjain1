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
        if(numberOfWheels <= 2){
            newSpot.setSpotType(SpotType.TWO_WHEELER);
            newSpot.setPricePerHour(pricePerHour);
        }
        else if(numberOfWheels <= 4){
            newSpot.setSpotType(SpotType.FOUR_WHEELER);
            newSpot.setPricePerHour(pricePerHour);
        }
        else{
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
        spotRepository1.deleteById(spotId);
    }
//    TestCases.testAddParkingLot:75 NullPointer
//    TestCases.testDeleteSpot:155 » NoSuchElement No value present
//    TestCases.testUpdateSpot:185 » NoSuchElement No value present
//    TestCases.testUpdateSpot1:224 » NoSuchElement No value present
//    TestCases.testDeleteParkingLot:249 » NoSuchElement No value present
//    TestCases.deleteUser_validInput_userDeleted:258 » NoSuchElement No value prese...
//    TestCases.updatePassword_validInput_passwordUpdated:276 NullPointer
//    TestCases.pay_validInput_success:635 » NullPointer
//    TestCases.pay_validInput_success1:657 » NullPointer
    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        Spot spot1 = null;
        for(Spot spot: spotList){
            if(spot.getId() == spotId){
                spot1 = spot;
                break;
            }
        }
        spot1.setPricePerHour(pricePerHour);
        spot1.setParkingLot(parkingLot);

        parkingLot.getSpotList().add(spot1);
        spotRepository1.save(spot1);
        return spot1;
//        updatedSpot.setOccupied(false);
//        updatedSpot.setPricePerHour(pricePerHour);
//        List<Spot> spotList = parkingLot.getSpotList();
//        spotList.add(updatedSpot);
//        parkingLot.setSpotList(spotList);
//
//        updatedSpot.setParkingLot(parkingLot);
//
//        parkingLotRepository1.save(parkingLot);
//        return updatedSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
//        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
//        List<Spot> spots = parkingLot.getSpotList();
//        for(Spot spot: spots){
//            spots.remove(spot);
//        }
//        parkingLotRepository1.delete(parkingLot);
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
