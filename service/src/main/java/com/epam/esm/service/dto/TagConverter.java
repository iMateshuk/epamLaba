package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {

    public static TagDTO toDto(TagEntity tagEntity) {
        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(tagEntity.getId());
        tagDTO.setName(tagEntity.getName());

        return tagDTO;
    }

    public static List<TagDTO> toDto(List<TagEntity> tagEntities) {

        return tagEntities.stream().map(TagConverter::toDto)
                .collect(Collectors.toList());
    }
}
