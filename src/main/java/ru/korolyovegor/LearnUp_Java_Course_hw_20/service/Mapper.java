package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.TicketDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.PremiereRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.TicketRepository;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    PremiereDto toDto(PremiereEntity premiereEntity);
    PremiereEntity toEntity(PremiereDto premiereDto);

    public default TicketDto toDto(TicketEntity ticketEntity, TicketRepository ticketRepository) {
        if ( ticketEntity == null ) {
            return null;
        }

        return TicketDto.builder()
                .id(ticketEntity.getId())
                .premiereId(ticketEntity.getPremiere().getId())
                .place(ticketEntity.getPlace()).build();
    }

    public default TicketEntity toEntity(TicketDto ticketDto, PremiereRepository premiereRepository) {
        PremiereEntity premiereEntity = premiereRepository.findById(ticketDto.getPremiereId()).get();
        return TicketEntity.builder()
                .id(ticketDto.getId())
                .premiere(premiereEntity)
                .place(ticketDto.getPlace()).build();
    }

}
