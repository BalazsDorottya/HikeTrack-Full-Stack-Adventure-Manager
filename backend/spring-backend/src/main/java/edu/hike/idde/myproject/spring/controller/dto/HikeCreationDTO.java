package edu.hike.idde.myproject.spring.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.Instant;

@Data
public class HikeCreationDTO {
    @NotBlank
    private String nameOfTrail;
    @NotBlank
    private String startLocation;
    @NotNull
    @FutureOrPresent
    private Instant startTime;
    @NotNull
    @Positive
    private Double price;
    @NotNull
    @Positive
    private Double lengthOfTrail;
}
