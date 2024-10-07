package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.CreateTagDTO;
import com.davidnguyen.backend.dto.UpdateTagsDTO;
import com.davidnguyen.backend.service.TagsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagsController {
    public final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostMapping("/create/tag")
    public Integer createTag(@Valid @RequestBody CreateTagDTO createTagDTO) {
        return tagsService.createTag(createTagDTO);
    }

    @PutMapping("/update/tags")
    public Integer updateTagsName(@Valid @RequestBody UpdateTagsDTO updateTagsDTO) {
        return tagsService.updateTagsName(updateTagsDTO);
    }
}
