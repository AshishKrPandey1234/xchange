package com.ashish.xchange.repository;

import com.ashish.xchange.model.SkillNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<SkillNode, String> {
}