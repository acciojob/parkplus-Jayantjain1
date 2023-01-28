package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pricePerHour;
    private boolean occupied;
    @Enumerated(value = EnumType.STRING)
    private SpotType spotType;

    public void setId(int id) {
        this.id = id;
    }


    public Spot(){

    }

    public int getId() {
        return id;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public Spot(boolean occupied, int pricePerHour){
        this.occupied = occupied;
        this.pricePerHour = pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot" , cascade = CascadeType.ALL)
    private List<Reservation> reservationList;
}
