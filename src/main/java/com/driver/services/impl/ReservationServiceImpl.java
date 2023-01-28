package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        User user = userRepository3.findById(userId).get();
        int minPrice = Integer.MAX_VALUE;
        List<Spot> spotList = parkingLot.getSpotList();
        Spot spot = null;
        if(numberOfWheels == 2){
            for(Spot spot1: spotList){
                if(spot1.getPricePerHour() * timeInHours < minPrice){
                    minPrice = spot1.getPricePerHour()*timeInHours;
                    spot = spot1;
                }
            }
            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        else if(numberOfWheels == 4){
            for(Spot spot1: spotList){
                if(spot1.getPricePerHour() * timeInHours < minPrice){
                    minPrice = spot1.getPricePerHour()*timeInHours;
                    spot = spot1;
                }
            }
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else{
            for(Spot spot1: spotList){
                if(spot1.getPricePerHour() * timeInHours < minPrice){
                    minPrice = spot1.getPricePerHour()*timeInHours;
                    spot = spot1;
                }
            }
            spot.setSpotType(SpotType.OTHERS);
        }
        if(parkingLot == null || user == null || (numberOfWheels >= 3 && spot.getSpotType().equals(SpotType.TWO_WHEELER))){
            throw new Exception("Cannot make reservation");
        } else if (parkingLot == null || user == null || (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.FOUR_WHEELER))){
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();

        reservation.setUser(user);
//        reservation.setNoOfHours(timeInHours);
        reservation.setNumberOfHours(timeInHours);
        user.getReservationList().add(reservation);

        spot.setPricePerHour(minPrice);
        reservation.setSpot(spot);
        spot.getReservationList().add(reservation);

        spotRepository3.save(spot);
        userRepository3.save(user);
        return reservation;
    }
}

//package com.driver.services.impl;
//
//import com.driver.model.*;
//import com.driver.repository.ParkingLotRepository;
//import com.driver.repository.ReservationRepository;
//import com.driver.repository.SpotRepository;
//import com.driver.repository.UserRepository;
//import com.driver.services.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ReservationServiceImpl implements ReservationService {
//    @Autowired
//    UserRepository userRepository3;
//    @Autowired
//    SpotRepository spotRepository3;
//    @Autowired
//    ReservationRepository reservationRepository3;
//    @Autowired
//    ParkingLotRepository parkingLotRepository3;
//    @Override
//    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
//        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
//        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
//        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
//
//    }
//}
