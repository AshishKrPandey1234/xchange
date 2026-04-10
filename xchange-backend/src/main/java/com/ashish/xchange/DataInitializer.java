package com.ashish.xchange;

import com.ashish.xchange.model.SkillNode;
import com.ashish.xchange.model.User;
import com.ashish.xchange.repository.SkillRepository;
import com.ashish.xchange.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public DataInitializer(UserRepository userRepository, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        skillRepository.deleteAll();

        // 1. Create 3 Users in MySQL
        User ashish = new User();
        ashish.setName("Ashish");
        ashish.setEmail("ashish@univ.com");
        ashish = userRepository.save(ashish);

        User rahul = new User();
        rahul.setName("Rahul");
        rahul.setEmail("rahul@univ.com");
        rahul = userRepository.save(rahul);

        User anjali = new User();
        anjali.setName("Anjali");
        anjali.setEmail("anjali@univ.com");
        anjali = userRepository.save(anjali);

        // 2. Create Skills in MongoDB and link to User IDs

        // Ashish: Offers Java, Wants Guitar
        saveSkill(ashish.getId(), "Java", "OFFER");
        saveSkill(ashish.getId(), "Guitar", "WANT");

        // Rahul: Offers Python, Wants Java
        saveSkill(rahul.getId(), "Python", "OFFER");
        saveSkill(rahul.getId(), "Java", "WANT");

        // Anjali: Offers Guitar, Wants Python
        saveSkill(anjali.getId(), "Guitar", "OFFER");
        saveSkill(anjali.getId(), "Python", "WANT");

        System.out.println("✅ 3-Way Trade Loop (Java -> Python -> Guitar) Seeded!");
    }

    // Helper method to save skills easily
    private void saveSkill(Long userId, String name, String type) {
        SkillNode s = new SkillNode();
        s.setUserId(userId);
        s.setSkillName(name);
        s.setType(type);
        skillRepository.save(s);
    }
}