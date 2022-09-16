package com.xatoxa.samobikes.entities;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @Column(name = "id_bike")
    private Integer id_bike;

    @OneToOne
    @JoinColumn(name = "id_bike")
    private Bike bike;

    @Column(name = "f_wheel")
    private boolean f_wheel;

    @Column(name = "r_wheel")
    private boolean r_wheel;

    @Column(name = "f_brake")
    private boolean f_brake;

    @Column(name = "r_brake")
    private boolean r_brake;

    @Column(name = "f_tyre")
    private boolean f_tyre;

    @Column(name = "r_tyre")
    private boolean r_tyre;

    @Column(name = "chain")
    private boolean chain;

    @Column(name = "saddle")
    private boolean saddle;

    @Column(name = "crank")
    private boolean crank;

    @Column(name = "gears")
    private boolean gears;

    @Column(name = "l_pedal")
    private boolean l_pedal;

    @Column(name = "r_pedal")
    private boolean r_pedal;

    @Column(name = "cassete")
    private boolean cassete;

    @Column(name = "chain_wheel")
    private boolean chain_wheel;

    @Column(name = "bot_bracket")
    private boolean bot_bracket;

    @Column(name = "steering_wheel")
    private boolean steering_wheel;

    //Construct
    public Part() {
    }

    public Part(Integer id_bike){
        this.id_bike = id_bike;
    }

    public Part(Integer id_bike, boolean f_wheel, boolean r_wheel, boolean f_brake, boolean r_brake, boolean f_tyre,
                boolean r_tyre, boolean chain, boolean saddle, boolean crank, boolean gears, boolean l_pedal,
                boolean r_pedal, boolean cassete, boolean chain_wheel, boolean bot_bracket, boolean steering_wheel) {
        this.id_bike = id_bike;
        this.f_wheel = f_wheel;
        this.r_wheel = r_wheel;
        this.f_brake = f_brake;
        this.r_brake = r_brake;
        this.f_tyre = f_tyre;
        this.r_tyre = r_tyre;
        this.chain = chain;
        this.saddle = saddle;
        this.crank = crank;
        this.gears = gears;
        this.l_pedal = l_pedal;
        this.r_pedal = r_pedal;
        this.cassete = cassete;
        this.chain_wheel = chain_wheel;
        this.bot_bracket = bot_bracket;
        this.steering_wheel = steering_wheel;
    }

    public Part(Integer id_bike, Bike bike, boolean f_wheel, boolean r_wheel, boolean f_brake, boolean r_brake,
                boolean f_tyre, boolean r_tyre, boolean chain, boolean saddle, boolean crank, boolean gears,
                boolean l_pedal, boolean r_pedal, boolean cassete, boolean chain_wheel, boolean bot_bracket,
                boolean steering_wheel) {
        this.id_bike = id_bike;
        this.bike = bike;
        this.f_wheel = f_wheel;
        this.r_wheel = r_wheel;
        this.f_brake = f_brake;
        this.r_brake = r_brake;
        this.f_tyre = f_tyre;
        this.r_tyre = r_tyre;
        this.chain = chain;
        this.saddle = saddle;
        this.crank = crank;
        this.gears = gears;
        this.l_pedal = l_pedal;
        this.r_pedal = r_pedal;
        this.cassete = cassete;
        this.chain_wheel = chain_wheel;
        this.bot_bracket = bot_bracket;
        this.steering_wheel = steering_wheel;
    }

    //getters, setters
    public Integer getId_bike() {
        return id_bike;
    }

    public void setId_bike(Integer id_bike) {
        this.id_bike = id_bike;
    }

    public boolean isF_wheel() {
        return f_wheel;
    }

    public void setF_wheel(boolean f_wheel) {
        this.f_wheel = f_wheel;
    }

    public boolean isR_wheel() {
        return r_wheel;
    }

    public void setR_wheel(boolean r_wheel) {
        this.r_wheel = r_wheel;
    }

    public boolean isF_brake() {
        return f_brake;
    }

    public void setF_brake(boolean f_brake) {
        this.f_brake = f_brake;
    }

    public boolean isR_brake() {
        return r_brake;
    }

    public void setR_brake(boolean r_brake) {
        this.r_brake = r_brake;
    }

    public boolean isF_tyre() {
        return f_tyre;
    }

    public void setF_tyre(boolean f_tyre) {
        this.f_tyre = f_tyre;
    }

    public boolean isR_tyre() {
        return r_tyre;
    }

    public void setR_tyre(boolean r_tyre) {
        this.r_tyre = r_tyre;
    }

    public boolean isChain() {
        return chain;
    }

    public void setChain(boolean chain) {
        this.chain = chain;
    }

    public boolean isSaddle() {
        return saddle;
    }

    public void setSaddle(boolean saddle) {
        this.saddle = saddle;
    }

    public boolean isCrank() {
        return crank;
    }

    public void setCrank(boolean crank) {
        this.crank = crank;
    }

    public boolean isGears() {
        return gears;
    }

    public void setGears(boolean gears) {
        this.gears = gears;
    }

    public boolean isL_pedal() {
        return l_pedal;
    }

    public void setL_pedal(boolean l_pedal) {
        this.l_pedal = l_pedal;
    }

    public boolean isR_pedal() {
        return r_pedal;
    }

    public void setR_pedal(boolean r_pedal) {
        this.r_pedal = r_pedal;
    }

    public boolean isCassete() {
        return cassete;
    }

    public void setCassete(boolean cassete) {
        this.cassete = cassete;
    }

    public boolean isChain_wheel() {
        return chain_wheel;
    }

    public void setChain_wheel(boolean chain_wheel) {
        this.chain_wheel = chain_wheel;
    }

    public boolean isBot_bracket() {
        return bot_bracket;
    }

    public void setBot_bracket(boolean bot_bracket) {
        this.bot_bracket = bot_bracket;
    }

    public boolean isSteering_wheel() {
        return steering_wheel;
    }

    public void setSteering_wheel(boolean steering_wheel) {
        this.steering_wheel = steering_wheel;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }


    //methods
    public void setAllTrue(){
        this.f_wheel = true;
        this.r_wheel = true;
        this.f_brake = true;
        this.r_brake = true;
        this.f_tyre = true;
        this.r_tyre = true;
        this.chain = true;
        this.saddle = true;
        this.crank = true;
        this.gears = true;
        this.l_pedal = true;
        this.r_pedal = true;
        this.cassete = true;
        this.chain_wheel = true;
        this.bot_bracket = true;
        this.steering_wheel = true;
    }


    public boolean checkWork(){
        if (!this.f_wheel) return false;
        if (!this.r_wheel) return false;
        if (!this.f_brake) return false;
        if (!this.r_brake) return false;
        if (!this.f_tyre) return false;
        if (!this.r_tyre) return false;
        if (!this.chain) return false;
        if (!this.saddle) return false;
        if (!this.crank) return false;
        if (!this.gears) return false;
        if (!this.l_pedal) return false;
        if (!this.r_pedal) return false;
        if (!this.cassete) return false;
        if (!this.chain_wheel) return false;
        if (!this.bot_bracket) return false;
        if (!this.steering_wheel) return false;

        return true;
    }
}
