package ru.korolyovegor.LearnUp_Java_Course_hw_20.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Premiere {
    private UUID id;
    private String name;
    private String description;
    private int ageCategory;
    private int quantityOfSeats;
    private int seatsUsed = 0;

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
