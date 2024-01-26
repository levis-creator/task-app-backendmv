package com.micosoft.taskappbackendmv.tags;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import com.micosoft.taskappbackendmv.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagsService {
    @Autowired
    TagsRepository tagsRepository;
    private final UserRepository userRepository;

    public List<Tags> getTags() {
        return tagsRepository.findAll();
    }

    public Tags getTag(Long id) {
        Optional<Tags> tagDb = tagsRepository.findById(id);
        if (tagDb.isPresent()){
            return tagDb.get();
        }else {
            throw new NotFoundException("tag does not exist");
        }
        
    }

    public Tags createTag(Tags tags) {
        Optional<Tags> tagsDb= tagsRepository.findByTagNameIgnoreCase(tags.getTagName());
        if (tagsDb.isEmpty()){
            return tagsRepository.save(tags);
        }else {
            throw new NotFoundException("Tag name already exists");
        }
    }

    public String deleteTag(Long id) {
        Optional<Tags> tagsDb= tagsRepository.findById(id);
        if (tagsDb.isPresent()){
            tagsRepository.deleteById(id);
            return "Deleted Successfully";
        }else {
            throw new NotFoundException("Tag does not exists");
        }
    }

    public Tags updateTag(Long id, Tags tags) {
        Optional<Tags> tagsDb=tagsRepository.findById(id);
        if (tagsDb.isEmpty()){
            throw new NotFoundException("Tag does not exist");
        }
        if (!tags.getTagName().isEmpty()&&!tags.getTagName().equals(tagsDb.get().getTagName())){
            tagsDb.get().setTagName(tags.getTagName());
        }
        if (!tags.getColors().isEmpty()&&!tags.getColors().equals(tagsDb.get().getColors())){
            tagsDb.get().setColors(tags.getColors());
        }
        return tagsRepository.save(tagsDb.get());

    }
}
