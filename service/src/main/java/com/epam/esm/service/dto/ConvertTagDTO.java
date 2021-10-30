package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertTagDTO {

    public static TagDTO getTagDTO(TagEntity tagEntity) {

        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(tagEntity.getId());
        tagDTO.setName(tagEntity.getName());

        return tagDTO;
    }

    public static List<TagDTO> getTagDTOs(List<TagEntity> tagEntities) {

        return tagEntities.stream().map(ConvertTagDTO::getTagDTO)
                .collect(Collectors.toList());
    }
}
