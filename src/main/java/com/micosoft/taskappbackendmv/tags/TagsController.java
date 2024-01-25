package com.micosoft.taskappbackendmv.tags;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
public class TagsController {
    @Autowired
    TagsService tagsService;
    @GetMapping
    public List <Tags> getTags(){
        return tagsService.getTags();
    }
    @GetMapping("{id}")
    public Tags getTag(@PathVariable String id){
        return tagsService.getTag(id);
    }
    @PostMapping
    public Tags createTag(@RequestBody Tags tags){
        return tagsService.createTag(tags);
    }
    @DeleteMapping
    public String deleteTag(@PathVariable String id){
        return tagsService.deleteTag(id);
    }
    @PutMapping("{id}")
    public  Tags updateTag(@PathVariable String id, @RequestBody Tags tags){
        return tagsService.updateTag(id, tags);
    }
}
