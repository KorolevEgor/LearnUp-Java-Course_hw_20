package ru.korolyovegor.LearnUp_Java_Course_hw_20.domain;

import lombok.*;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class Premiere implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private int ageCategory;
    private int quantityOfSeats;
    private int seatsUsed = 0;

    private Set<TicketEntity> ticketSet;

    public boolean isFreeSeat() {
        return seatsUsed < quantityOfSeats;
    }

    public void book() {
        seatsUsed++;
    }

    public void unband() {
        seatsUsed--;
    }

    @Override
    public String toString() {
        return "Премьера:" +
                "    название: " + name + '\n' +
                "    описание: " + description + '\n' +
                "    возрастное ограничение: " + ageCategory + "+\n" +
                "    количество мест: " + quantityOfSeats + '\n' +
                "    (" +  id + ")";
    }
}
