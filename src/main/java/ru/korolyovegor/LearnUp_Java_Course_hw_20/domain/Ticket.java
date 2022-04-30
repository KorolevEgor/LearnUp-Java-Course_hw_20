package ru.korolyovegor.LearnUp_Java_Course_hw_20.domain;

import lombok.*;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class Ticket implements Serializable {
    private UUID id;
    private UUID premiere;
    private String place;

    public UUID getPremiere() {
        return premiere;
    }

    public void setPremiere(UUID premiere) {
        this.premiere = premiere;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", place='" + place + '\'' +
                '}';
    }
}
