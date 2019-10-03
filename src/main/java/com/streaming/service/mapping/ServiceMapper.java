package com.streaming.service.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper<O, E> extends AbstractServiceMapper {
    @Resource
    private ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }


    public void mapDTOToEntity(final O dto, final E entity) {
        if (dto == null) {
            return;
        }

        map(dto, entity);

    }

    public void mapEntityToDTO(final E entity, final O dto) {
        if (dto == null) {
            return;
        }

        map(entity, dto);

    }

    public O mapEntityToDTO(final E entity, final Class<O> clazz) {
        if (entity == null) {
            return null;
        }

        return map(entity, clazz);
    }

    public List<O> mapEntitiesToDTOs(final List<E> entities, final Class<O> clazz) {
        List<O> list = new ArrayList<>();

        for (E enitity : entities) {
            list.add(mapEntityToDTO(enitity, clazz));
        }

        return list;
    }

    public List<O> mapEntitiesToDTOs(final Iterable<E> entities, final Class<O> clazz) {
        List<O> list = new ArrayList<>();

        for (E enitity : entities) {
            list.add(mapEntityToDTO(enitity, clazz));
        }

        return list;
    }
}
