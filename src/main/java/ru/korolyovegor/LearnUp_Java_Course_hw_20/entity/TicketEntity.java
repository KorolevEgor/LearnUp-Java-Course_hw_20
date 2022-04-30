package ru.korolyovegor.LearnUp_Java_Course_hw_20.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tickets")
public class TicketEntity implements Serializable {
    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premiere")
    private PremiereEntity premiere;

    @Column(name = "place")
    private String place;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", place='" + place + '\'' +
                '}';
    }
}
