package ru.korolyovegor.LearnUp_Java_Course_hw_20.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketDto {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private UUID premiereId;
//    private PremiereDto premiere;

    @JsonProperty
    private String place;

    @Override
    public String toString() {
        return "Ticket{" + "\n" +
                "id=" + id + "\n" +
                "premiere=" + premiereId + "\n" +
                ", place='" + place + "'\n" +
                '}';
    }
}
