package com.ashish.xchange.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skills")
@Data
public class SkillNode {
    @Id
    private String id;
    private Long userId; // This links to the MySQL User ID
    private String skillName;
    private String type; // We will use "OFFER" or "WANT"
}