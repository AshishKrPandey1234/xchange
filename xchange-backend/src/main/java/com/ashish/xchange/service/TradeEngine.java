package com.ashish.xchange.service;

import com.ashish.xchange.model.SkillNode;
import com.ashish.xchange.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TradeEngine {

    @Autowired
    private SkillRepository skillRepository;

    // The core Graph: Map<UserId, List of UserIds who have what this user wants>
    private Map<Long, List<Long>> adjList = new HashMap<>();

    public List<Long> detectCycle(Long startUserId) {
        List<SkillNode> allSkills = skillRepository.findAll();
        buildGraph(allSkills);

        Set<Long> visited = new HashSet<>();
        Set<Long> recStack = new HashSet<>();
        List<Long> path = new ArrayList<>();

        if (dfs(startUserId, visited, recStack, path)) {
            return path;
        }
        return Collections.emptyList();
    }

    private void buildGraph(List<SkillNode> skills) {
        adjList.clear();
        for (SkillNode u1 : skills) {
            if (!u1.getType().equals("WANT")) continue;

            for (SkillNode u2 : skills) {
                if (u2.getType().equals("OFFER") && u1.getSkillName().equalsIgnoreCase(u2.getSkillName())) {
                    adjList.computeIfAbsent(u1.getUserId(), k -> new ArrayList<>()).add(u2.getUserId());
                }
            }
        }
    }

    private boolean dfs(Long u, Set<Long> visited, Set<Long> recStack, List<Long> path) {
        visited.add(u);
        recStack.add(u);
        path.add(u);

        for (Long v : adjList.getOrDefault(u, new ArrayList<>())) {
            if (recStack.contains(v)) {
                path.add(v); // Cycle found!
                return true;
            }
            if (!visited.contains(v) && dfs(v, visited, recStack, path)) {
                return true;
            }
        }

        recStack.remove(u);
        path.remove(path.size() - 1);
        return false;
    }
}