package com.micosoft.taskappbackendmv.tags;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagsServiceTest {
    @Mock
    TagsRepository tagsRepository;
    @InjectMocks
    TagsService tagsService;

    Tags tag;

    String tagName = "React";
   Long tagId=1L;
    Tags updateTag = Tags.builder().tagId(1L).tagName("Something else").colors("#ff8").build();

    @BeforeEach
    void setUp() {
        tag = Tags.builder().tagId(1L).tagName("React").colors("#FFFF").build();
    }

    @Test
    void checkingIfAllTagsBeingBroughtFromDb() {
        tagsService.getTags();
        verify(tagsRepository).findAll();
    }

    @Test
    void gettingTagWhenExist() {
        when(tagsRepository.findById(tagId)).thenReturn(Optional.of(tag));
        assertThat(tagsService.getTag(tagId)).isNotNull();
    }

    @Test
    void gettingTagWhenDoesNotExist() {
        when(tagsRepository.findById(tagId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tagsService.getTag(tagId));
    }

    @Test
    void creatingTagWhenExists() {
        when(tagsRepository.findByTagNameIgnoreCase(tag.getTagName())).thenReturn(Optional.of(tag));
        assertThrows(NotFoundException.class, () -> tagsService.createTag(tag));
        verify(tagsRepository, never()).save(any());
    }

    @Test
    void creatingTagWhenDoesNotExist() {
        when(tagsRepository.findByTagNameIgnoreCase(tag.getTagName())).thenReturn(Optional.empty());
        tagsService.createTag(tag);
        verify(tagsRepository).save(tag);
    }

    @Test
    void deletingTagWhenTagDoesNotExist() {
        when(tagsRepository.findById(tagId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tagsService.deleteTag(tagId));
        verify(tagsRepository, never()).deleteById(tagId);
    }

    @Test
    void deletingTagWhenTagExists() {
        when(tagsRepository.findById(tagId)).thenReturn(Optional.of(tag));
        assertThat(tagsService.deleteTag(tagId)).isNotNull();
        verify(tagsRepository).deleteById(tagId);
    }

    @Test
    void updatingTagWhenTagDoeNotExist() {
        when(tagsRepository.findByTagNameIgnoreCase(tagName)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tagsService.updateTag(tagId, tag));
        verify(tagsRepository, never()).save(any());
    }

    @Test
    void updatingTagWhenTagExists() {
        when(tagsRepository.findById(tagId)).thenReturn(Optional.of(tag));when(tagsRepository.findByTagNameIgnoreCase(updateTag.getTagName())).thenReturn(Optional.empty());
        when(tagsRepository.save(tag)).thenReturn(tag);
        Tags result = tagsService.updateTag(tagId, updateTag);
        assertAll(
                () -> assertEquals(updateTag.getTagName(), result.getTagName()),
                () -> assertEquals(updateTag.getColors(), result.getColors())
        );

    }



}