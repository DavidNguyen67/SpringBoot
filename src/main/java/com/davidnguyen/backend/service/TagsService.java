package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateTagDTO;
import com.davidnguyen.backend.dto.UpdateTagsDTO;
import com.davidnguyen.backend.repository.TagsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TagsService {
    private static final Logger log = LoggerFactory.getLogger(TagsService.class);
    private final TagsRepository tagsRepository;

    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createTag(CreateTagDTO createTagDTO) {
        try {
            // Check if tag name already exists
            if (!tagsRepository.findTagsByName(createTagDTO.getName()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag already exists");
            }

            // Create tag
            Integer resultCreateTag = tagsRepository.createTag(createTagDTO.getId(), createTagDTO.getName());

            if (resultCreateTag == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create tag");
            }

            return resultCreateTag;
        } catch (Exception e) {
            log.error("Error creating tag: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer updateTagsName(UpdateTagsDTO updateTagsDTO) {
        try {
            // Check if tags exist
            if (tagsRepository.findTagsByIds(updateTagsDTO.getIds()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tags not found");
            }

            // Update tag names
            Integer resultUpdateTags = tagsRepository.updateTagsName(updateTagsDTO.getIds(), updateTagsDTO.getNewNameTag());

            if (resultUpdateTags == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update tags");
            }

            return resultUpdateTags;
        } catch (Exception e) {
            log.error("Error updating tags: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }
}