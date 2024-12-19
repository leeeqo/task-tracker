package com.leeeqo.mapper

import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

interface EntityMapper<Entity, RequestDTO, ResponseDTO> {

    @Mapping(target = "id", ignore = true)
    fun mapToEntity(request: RequestDTO): Entity

    fun mapToResponse(entity: Entity): ResponseDTO

    @Mapping(target = "id", ignore = true)
    fun update(request: RequestDTO, @MappingTarget entity: Entity): Entity
}
