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

        try {

            if (!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()) {
                throw new Exception("Cannot make reservation");
            }
            User user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spotList = parkingLot.getSpotList();
            boolean checkForSpots = false;
            for (Spot spot : spotList) {
                if (!spot.getOccupied()) {
                    checkForSpots = true;
                    break;
                }
            }

            if (!checkForSpots) {
                throw new Exception("Cannot make reservation");
            }


            SpotType requestSpotType;

            if (numberOfWheels > 4) {
                requestSpotType = SpotType.OTHERS;
            } else if (numberOfWheels > 2) {
                requestSpotType = SpotType.FOUR_WHEELER;
            } else requestSpotType = SpotType.TWO_WHEELER;


            int minimumPrice = Integer.MAX_VALUE;

            checkForSpots = false;

            Spot spotChosen = null;

            for (Spot spot : spotList) {
                if (requestSpotType.equals(SpotType.OTHERS) && spot.getSpotType().equals(SpotType.OTHERS)) {
                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
                        minimumPrice = spot.getPricePerHour() * timeInHours;
                        checkForSpots = true;
                        spotChosen = spot;
                    }
                } else if (requestSpotType.equals(SpotType.FOUR_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS) ||
                        spot.getSpotType().equals(SpotType.FOUR_WHEELER)) {
                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
                        minimumPrice = spot.getPricePerHour() * timeInHours;
                        checkForSpots = true;
                        spotChosen = spot;
                    }
                } else if (requestSpotType.equals(SpotType.TWO_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS) ||
                        spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.TWO_WHEELER)) {
                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
                        minimumPrice = spot.getPricePerHour() * timeInHours;
                        checkForSpots = true;
                        spotChosen = spot;
                    }
                }

            }

            if (!checkForSpots) {
                throw new Exception("Cannot make reservation");
            }

            spotChosen.setOccupied(true);

            Reservation reservation = new Reservation();
            reservation.setNumberOfHours(timeInHours);
            reservation.setSpot(spotChosen);
            reservation.setUser(user);

            //Bidirectional
            spotChosen.getReservationList().add(reservation);
            user.getReservationList().add(reservation);

            userRepository3.save(user);
            spotRepository3.save(spotChosen);

            return reservation;
        } catch (Exception e) {
            return null;
        }
    }
}
//try{
//            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//            User user = userRepository3.findById(userId).get();
//            int minPrice = Integer.MAX_VALUE;
//            List<Spot> spotList = parkingLot.getSpotList();
//            Spot spot = null;
//            if (numberOfWheels <= 2) {
//                for (Spot spot1 : spotList) {
//                    if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                        minPrice = spot1.getPricePerHour() * timeInHours;
//                        spot = spot1;
//                    }
//                }
//                spot.setSpotType(SpotType.TWO_WHEELER);
//            } else if (numberOfWheels <= 4) {
//                for (Spot spot1 : spotList) {
//                    if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                        minPrice = spot1.getPricePerHour() * timeInHours;
//                        spot = spot1;
//                     }
//                }
//                spot.setSpotType(SpotType.FOUR_WHEELER);
//            } else {
//                for (Spot spot1 : spotList) {
//                    if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                        minPrice = spot1.getPricePerHour() * timeInHours;
//                        spot = spot1;
//                    }
//                }
//                spot.setSpotType(SpotType.OTHERS);
//            }
////            if (parkingLot == null || user == null) {
////                return null;
////            }
////        } else if (parkingLot == null || user == null || (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.FOUR_WHEELER))){
////            throw new Exception("Cannot make reservation");
////        }
//           if (!spot.getOccupied() || (numberOfWheels >= 3 && spot.getSpotType().equals(SpotType.TWO_WHEELER))) {
//               throw new Exception("Cannot make reservation");
//           }
//           if (!spot.getOccupied() || (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.FOUR_WHEELER))) {
//               throw new Exception("Cannot make reservation");
//           }
//
//           Reservation reservation = new Reservation();
//
//           reservation.setUser(user);
//           reservation.setNumberOfHours(timeInHours);
//           user.getReservationList().add(reservation);
//
//           reservation.setSpot(spot);
//           spot.getReservationList().add(reservation);
//
//           spotRepository3.save(spot);
//           userRepository3.save(user);
//           return reservation;
//        }
//        catch (Exception e){
//            return null;
//        }
//        try{
//            if(!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()){
//                throw new Exception("Cannot make reservation");
//            }
//            User user = userRepository3.findById(userId).get();
//            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//
//            List<Spot> spotList = parkingLot.getSpotList();
//            boolean gotSpot = false;
//            for(Spot spot: spotList){
//                if(!spot.getOccupied()){
//                    gotSpot = true;
//                    break;
//                }
//            }
//            if(!gotSpot){
//                throw new Exception("Cannot make reservation");
//            }
//            SpotType newSpotType;
//            if(numberOfWheels > 4){
//                newSpotType = SpotType.OTHERS;
//            }
//            else if(numberOfWheels >= 3){
//                newSpotType = SpotType.FOUR_WHEELER;
//            }
//            else{
//                newSpotType = SpotType.TWO_WHEELER;
//            }
//            int minPrice = Integer.MAX_VALUE;
//            Spot newSpot = null;
//            for(Spot spot: spotList){
//                if(spot.getSpotType().equals(SpotType.FOUR_WHEELER) && newSpotType.equals(SpotType.FOUR_WHEELER) && !spot.getOccupied()){
//                    if(spot.getPricePerHour() * timeInHours < minPrice){
//                        minPrice = spot.getPricePerHour() * timeInHours;
//                        newSpot = spot;
//                    }
//                }
//                else if(!spot.getOccupied() && spot.getSpotType().equals(SpotType.TWO_WHEELER) && newSpotType.equals(SpotType.TWO_WHEELER)){
//                    if(spot.getPricePerHour() * timeInHours < minPrice){
//                        minPrice = spot.getPricePerHour() * timeInHours;
//                        newSpot = spot;
//                    }
//                }
//                else if(!spot.getOccupied() && spot.getSpotType().equals(SpotType.OTHERS) && newSpotType.equals(SpotType.OTHERS)){
//                    if(spot.getPricePerHour() * timeInHours < minPrice){
//                        minPrice = spot.getPricePerHour() * timeInHours;
//                        newSpot = spot;
//                    }
//                }
//            }
//
//            Reservation reservation = new Reservation();
//            reservation.setNumberOfHours(timeInHours);
//            reservation.setSpot(newSpot);
//
//            newSpot.getReservationList().add(reservation);
//
//            reservation.setUser(user);
//            user.getReservationList().add(reservation);
//            spotRepository3.save(newSpot);
//            userRepository3.save(user);
//            return reservation;
//        }
//        catch (Exception e){
//            return null;
//        }
//    }
//}
//        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//        User user = userRepository3.findById(userId).get();
//        int minPrice = Integer.MAX_VALUE;
//        List<Spot> spotList = parkingLot.getSpotList();
//        Spot spot = null;
//        if (numberOfWheels <= 2) {
//            for (Spot spot1 : spotList) {
//                if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                    minPrice = spot1.getPricePerHour() * timeInHours;
//                    spot = spot1;
//                }
//            }
//            spot.setSpotType(SpotType.TWO_WHEELER);
//        } else if (numberOfWheels <= 4) {
//            for (Spot spot1 : spotList) {
//                if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                    minPrice = spot1.getPricePerHour() * timeInHours;
//                    spot = spot1;
//                }
//            }
//            spot.setSpotType(SpotType.FOUR_WHEELER);
//        } else {
//            for (Spot spot1 : spotList) {
//                if (spot1.getPricePerHour() * timeInHours < minPrice) {
//                    minPrice = spot1.getPricePerHour() * timeInHours;
//                    spot = spot1;
//                }
//            }
//            spot.setSpotType(SpotType.OTHERS);
//        }
//        if (parkingLot == null || user == null) {
//            return null;
//        }
////        } else if (parkingLot == null || user == null || (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.FOUR_WHEELER))){
////            throw new Exception("Cannot make reservation");
////        }
//        if (!spot.getOccupied() || (numberOfWheels >= 3 && spot.getSpotType().equals(SpotType.TWO_WHEELER))) {
//            throw new Exception("Cannot make reservation");
//        }
//        if (!spot.getOccupied() || (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.FOUR_WHEELER))) {
//            throw new Exception("Cannot make reservation");
//        }
//
//        Reservation reservation = new Reservation();
//
//        reservation.setUser(user);
//        reservation.setNumberOfHours(timeInHours);
//        user.getReservationList().add(reservation);
//
//        reservation.setSpot(spot);
//        spot.getReservationList().add(reservation);
//
//        spotRepository3.save(spot);
//        userRepository3.save(user);
//        return reservation;
//}

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
