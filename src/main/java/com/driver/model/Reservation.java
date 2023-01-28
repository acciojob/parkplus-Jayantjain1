package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int NumberOfHours;

    public Reservation(){

    }
    public Reservation(int NumberOfHours){
        this.NumberOfHours = NumberOfHours;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Spot getSpot() {
        return spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public int getNumberOfHours() {
        return NumberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        NumberOfHours = numberOfHours;
    }

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Spot spot;

    @OneToOne(mappedBy = "reservation" , cascade = CascadeType.ALL)
    private Payment payment;
}
