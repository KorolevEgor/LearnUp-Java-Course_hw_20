package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Ticket;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.TicketDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    PremiereDto toDto(PremiereEntity premiereEntity);
    PremiereDto toDto(Premiere premiere);
    Premiere toDomain(PremiereDto premiereDto);
    PremiereEntity toEntity(Premiere premiere);
    PremiereEntity toEntity(PremiereDto premiereDto);
    Premiere toDomain(PremiereEntity premiereEntity);


    TicketDto toDto(TicketEntity ticketEntity);
    TicketDto toDto(Ticket ticket);
    Ticket toDomain(TicketDto ticketDto);
    TicketEntity toEntity(Ticket ticket);
    TicketEntity toEntity(TicketDto ticketDto);
    Ticket toDomain(TicketEntity ticketEntity);

}
