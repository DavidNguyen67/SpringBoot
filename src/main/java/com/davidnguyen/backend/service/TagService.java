package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateTagDTO;
import com.davidnguyen.backend.dto.DeleteTagDTO;
import com.davidnguyen.backend.dto.UpdateTagsDTO;
import com.davidnguyen.backend.model.Tag;
import com.davidnguyen.backend.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Slf4j
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createTag(CreateTagDTO createTagDTO) {
        try {
            // Check if tag name already exists
            if (!tagRepository.findTagsByName(createTagDTO.getName()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag already exists");
            }

            // Create tag
            Integer resultCreateTag = tagRepository.createTag(createTagDTO.getId(), createTagDTO.getName());

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
            if (tagRepository.findTagsByIds(updateTagsDTO.getIds()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tags not found");
            }

            // Update tag names
            Integer resultUpdateTags = tagRepository.updateTagsName(updateTagsDTO.getIds(), updateTagsDTO.getNewNameTag());

            if (resultUpdateTags == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update tags");
            }

            return resultUpdateTags;
        } catch (Exception e) {
            log.error("Error updating tags: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer deleteSoftTagsProductByIds(DeleteTagDTO deleteTagDTO) {
        try {
            // Check if tags exist
            if (tagRepository.findTagsByIds(deleteTagDTO.getTagIds()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tags not found");
            }

            // Delete tags
            Integer resultDeleteTags = tagRepository.deleteSoftTagsProductByIds(deleteTagDTO.getTagIds());

            if (resultDeleteTags == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete tags");
            }

            return resultDeleteTags;
        } catch (Exception e) {
            log.error("Error deleting tags: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    public List<Tag> findTagsWithPagination(Integer offset, Integer limit) {
        try {
            return tagRepository.findTagsWithPagination(offset, limit);
        } catch (Exception e) {
            log.error("Error finding tags with pagination: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    public List<Tag> findTagsByIds(List<String> ids) {
        try {
            return tagRepository.findTagsByIds(ids);
        } catch (Exception e) {
            log.error("Error finding tags by ids: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }
}