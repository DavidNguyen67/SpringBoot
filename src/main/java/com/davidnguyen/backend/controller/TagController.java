package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.CreateTagDTO;
import com.davidnguyen.backend.dto.DeleteTagDTO;
import com.davidnguyen.backend.dto.UpdateTagsDTO;
import com.davidnguyen.backend.model.Tag;
import com.davidnguyen.backend.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    public final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> findTagsWithPagination(@Valid @RequestParam("offset") @Min(0) Integer offset, @Valid @RequestParam("limit") @Min(1) @Max(50) Integer limit) {
        return ResponseEntity.ok(tagService.findTagsWithPagination(offset, limit));
    }

    @PostMapping("/create/tag")
    public ResponseEntity<Integer> createTag(@Valid @RequestBody CreateTagDTO createTagDTO) {
        return ResponseEntity.ok(tagService.createTag(createTagDTO));
    }

    @PutMapping("/update/tags")
    public ResponseEntity<Integer> updateTagsName(@Valid @RequestBody UpdateTagsDTO updateTagsDTO) {
        return ResponseEntity.ok(tagService.updateTagsName(updateTagsDTO));
    }

    @DeleteMapping("/delete/tags")
    public ResponseEntity<Integer> deleteSoftTagsProductByIds(@Valid @RequestParam DeleteTagDTO deleteTagDTO) {
        return ResponseEntity.ok(tagService.deleteSoftTagsProductByIds(deleteTagDTO));
    }
}
