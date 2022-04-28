package ru.korolyovegor.LearnUp_Java_Course_hw_20.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "premieres")
public class PremiereEntity {
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
    private Set<TicketEntity> ticketSet;

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
