package edu.hike.idde.myproject.spring.model;

import java.time.Instant;

public class Hike extends BaseEntity {

    private String nameOfTrail;
    private String startLocation;
    private Instant startTime;
    private Double price;
    private Double lengthOfTrail;


    public Hike() {
        super();
    }

    public Hike(Long id, String nameOfTrail, String startLocation, Instant startTime, Double price,
                Double lengthOfTrail) {
        super(id);
        this.nameOfTrail = nameOfTrail;
        this.startLocation = startLocation;
        this.startTime = startTime;
        this.price = price;
        this.lengthOfTrail = lengthOfTrail;
    }

    public String getNameOfTrail() {
        return nameOfTrail;
    }

    public void setNameOfTrail(String nameOfTrail) {
        this.nameOfTrail = nameOfTrail;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLengthOfTrail() {
        return lengthOfTrail;
    }

    public void setLengthOfTrail(Double lengthOfTrail) {
        this.lengthOfTrail = lengthOfTrail;
    }


    @Override
    public String toString() {
        return "Hike{"
                + "nameOfTrail='" + nameOfTrail + '\''
                + ", startLocation='" + startLocation + '\''
                + ", startTime=" + startTime
                + ", price=" + price
                + ", lengthOfTrail=" + lengthOfTrail
                + '}';
    }
}
