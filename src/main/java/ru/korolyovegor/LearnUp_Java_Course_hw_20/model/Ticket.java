package ru.korolyovegor.LearnUp_Java_Course_hw_20.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Ticket {
    private UUID id;
    private Premiere premiere;
    private String place;
}
