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
