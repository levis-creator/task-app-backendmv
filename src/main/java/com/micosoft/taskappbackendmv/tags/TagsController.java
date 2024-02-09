package com.micosoft.taskappbackendmv.tags;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
public class TagsController {
    @Autowired
    private  final TagsService tagsService;
    @GetMapping
    public List <Tags> getTags(){
        return tagsService.getTags();
    }
    @GetMapping("{id}")
    public Tags getTag(@PathVariable Long id){
        return tagsService.getTag(id);
    }
    @PostMapping
    public Tags createTag(@RequestBody Tags tags){
        return tagsService.createTag(tags);
    }
    @DeleteMapping("{id}")
    public String deleteTag(@PathVariable Long id){
        return tagsService.deleteTag(id);
    }
    @PutMapping("{id}")
    public  Tags updateTag(@PathVariable Long id, @RequestBody Tags tags){
        return tagsService.updateTag(id, tags);
    }
}