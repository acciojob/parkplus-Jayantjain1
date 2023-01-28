package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean PaymentCompleted;
    @Enumerated(value = EnumType.STRING)
    private PaymentMode paymentMode;

    public Reservation getReservation() {
        return reservation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment(){

    }
    public Payment(boolean PaymentCompleted){
        this.PaymentCompleted = PaymentCompleted;
    }

    public boolean isPaymentCompleted() {
        return PaymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        PaymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }



    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @OneToOne
    @JoinColumn
    private Reservation reservation;
}
