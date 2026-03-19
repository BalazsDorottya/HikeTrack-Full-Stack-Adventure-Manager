package edu.hike.idde.myproject.spring.controller.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class HikeLongDTO {
    private Long id;
    private String nameOfTrail;
    private String startLocation;
    private Instant startTime;
    private Double price;
    private Double lengthOfTrail;
    private Boolean favorite;
    private Integer timesCompleted;
}
