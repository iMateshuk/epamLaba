package com.epam.esm.controller;

import com.epam.esm.exception.ControllerException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final ControllerValidator controllerValidator;

    public TagController(TagService tagService, ControllerValidator controllerValidator) {

        this.tagService = tagService;
        this.controllerValidator = controllerValidator;
    }


    @PostMapping()
    public TagDTO createTag(@RequestBody TagDTO tagDTO) throws ControllerException {

        controllerValidator.validateValueOfTagName(tagDTO.getName());
        return tagService.createTag(tagDTO.getName());
    }

    @GetMapping()
    public List<TagDTO> getTags() {

        return tagService.searchTags();
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable int id) throws ControllerException {

        controllerValidator.validateValueOfId(id);
        return tagService.searchTag(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable int id) throws ControllerException {

        controllerValidator.validateValueOfId(id);
        tagService.deleteTag(id);
    }
}
