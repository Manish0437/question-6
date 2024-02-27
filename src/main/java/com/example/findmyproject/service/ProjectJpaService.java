/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here

package com.example.findmyproject.service;

import com.example.findmyproject.model.Researcher;
import com.example.findmyproject.model.Project;
import com.example.findmyproject.repository.ResearcherJpaRepository;
import com.example.findmyproject.repository.ProjectJpaRepository;
import com.example.findmyproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectJpaService implements ProjectRepository {

    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Autowired
    private ProjectJpaRepository projectJpaRepository;

    public ArrayList<Project> getProjects() {
        List<Project> projectList = projectJpaRepository.findAll();
        ArrayList<Project> projects = new ArrayList<>(projectList);
        return projects;
    }

    public Project getProjectById(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            return project;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Project addProject(Project project) {
        List<Integer> researcherIds = new ArrayList<>();
        for (Researcher researcher : project.getResearchers()) {
            researcherIds.add(researcher.getResearcherId());
        }

        List<Researcher> researchers = researcherJpaRepository.findAllById(researcherIds);
        if (researchers.size() != researcherIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        project.setResearchers(researchers);

        for (Researcher researcher : researchers) {
            researcher.getProjects().add(project);
        }

        Project savedProject = projectJpaRepository.save(project);
        researcherJpaRepository.saveAll(researchers);

        return savedProject;
    }

    public Project updateProject(int projectId, Project project) {
        try {
            Project newProject = projectJpaRepository.findById(projectId).get();
            if (project.getProjectName() != null) {
                newProject.setProjectName(project.getProjectName());
            }
            if (project.getBudget() != 0.0) {
                newProject.setBudget(project.getBudget());
            }
            if (project.getResearchers() != null) {
                List<Researcher> researchers = newProject.getResearchers();
                for (Researcher researcher : researchers) {
                    researcher.getProjects().remove(newProject);
                }
                researcherJpaRepository.saveAll(researchers);
                List<Integer> newResearcherIds = new ArrayList<>();

                for (Researcher researcher : project.getResearchers()) {
                    newResearcherIds.add(researcher.getResearcherId());
                }
                List<Researcher> newResearchers = researcherJpaRepository.findAllById(newResearcherIds);

                if (newResearchers.size() != newResearcherIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Researcher researcher : newResearchers) {
                    researcher.getProjects().add(newProject);
                }
                researcherJpaRepository.saveAll(newResearchers);
                newProject.setResearchers(newResearchers);
            }
            return projectJpaRepository.save(newProject);
        } 
        
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteProject(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();

            List<Researcher> researchers = project.getResearchers();
            for (Researcher researcher : researchers) {
                researcher.getProjects().remove(project);
            }

            researcherJpaRepository.saveAll(researchers);

            projectJpaRepository.deleteById(projectId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Researcher> getProjectResearchers(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            return project.getResearchers();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
