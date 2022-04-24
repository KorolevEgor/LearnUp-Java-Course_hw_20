package ru.korolyovegor.LearnUp_Java_Course_hw_20.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "premieres")
public class Premiere implements Serializable {
    @Id
    @Column(name = "id")
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "age_category")
    private int ageCategory;

    @Column(name = "quantity_of_seats")
    private int quantityOfSeats;

    @Column(name = "seats_used")
    private int seatsUsed = 0;

    @OneToMany(mappedBy = "premiere", fetch = FetchType.LAZY)
    private Set<Ticket> ticketSet;

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
