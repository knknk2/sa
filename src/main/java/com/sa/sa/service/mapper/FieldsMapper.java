package com.sa.sa.service.mapper;

import com.sa.sa.domain.*;
import com.sa.sa.service.dto.FieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fields} and its DTO {@link FieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FieldsMapper extends EntityMapper<FieldsDTO, Fields> {



    default Fields fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fields fields = new Fields();
        fields.setId(id);
        return fields;
    }
}
