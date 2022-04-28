package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    PremiereDto toDto(PremiereEntity premiereEntity);
    PremiereDto toDto(Premiere premiere);
    Premiere toDomain(PremiereDto premiereDto);
    PremiereEntity toEntity(Premiere premiere);
    PremiereEntity toEntity(PremiereDto premiereDto);
    Premiere toDomain(PremiereEntity premiereEntity);

}
