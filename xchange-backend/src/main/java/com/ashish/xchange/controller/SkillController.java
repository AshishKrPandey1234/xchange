package com.ashish.xchange.controller;

import com.ashish.xchange.model.SkillNode;
import com.ashish.xchange.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<SkillNode> getAllSkills() {
        return skillRepository.findAll();
    }

    @PostMapping
    public SkillNode addSkill(@RequestBody SkillNode skill) {
        return skillRepository.save(skill);
    }
}