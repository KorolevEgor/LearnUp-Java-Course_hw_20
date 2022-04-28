package ru.korolyovegor.LearnUp_Java_Course_hw_20.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class PremiereDto {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private int ageCategory;

    @JsonProperty
    private int quantityOfSeats;

    @JsonProperty
    private int seatsUsed = 0;
}
