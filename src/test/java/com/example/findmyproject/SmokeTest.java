package com.example.findmyproject;

import com.example.findmyproject.controller.ResearcherController;
import com.example.findmyproject.controller.ProjectController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ResearcherController researchController;

    @Autowired
    private ProjectController projectController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(researchController).isNotNull();
        assertThat(projectController).isNotNull();
    }
}
