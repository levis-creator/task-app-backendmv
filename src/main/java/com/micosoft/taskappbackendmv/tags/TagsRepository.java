package com.micosoft.taskappbackendmv.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, String> {
    Optional<Tags> findByTagNameIgnoreCase(String tagName);
}