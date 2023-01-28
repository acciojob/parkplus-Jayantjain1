package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isCompleted;
    @Enumerated(value = EnumType.STRING)
    private PaymentMode paymentMode;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment(){

    }
    public Payment(boolean isCompleted){
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @OneToOne
    @JoinColumn
    private Reservation reservation;
}
