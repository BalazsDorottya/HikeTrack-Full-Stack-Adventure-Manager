package edu.hike.idde.myproject.spring.controller.dto;

import lombok.Data;

@Data
public class HikeShortDTO {
    private Long id;
    private String nameOfTrail;
    private Boolean favorite;
    private Integer timesCompleted;
}
