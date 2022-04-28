package ru.korolyovegor.LearnUp_Java_Course_hw_20.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class TicketDto {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private PremiereEntity premiere;

    @JsonProperty
    private String place;
}
