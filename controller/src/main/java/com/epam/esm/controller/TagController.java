package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {

        this.tagService = tagService;
    }


    @PostMapping("/creators")
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {

        return tagService.createTag(tagDTO.getName());
    }

    @GetMapping()
    public List<TagDTO> getTags() {

        return tagService.searchTags();
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable int id) {

        return tagService.searchTag(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable int id) {

        tagService.deleteTag(id);
    }
}
