/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here

package com.example.findmyproject.repository;

import com.example.findmyproject.model.Project;
import com.example.findmyproject.model.Researcher;

import java.util.ArrayList;
import java.util.List;

public interface ProjectRepository {
    ArrayList<Project> getProjects();

    Project getProjectById(int projectId);

    Project addProject(Project project);

    Project updateProject(int projectId, Project project);

    void deleteProject(int projectId);

    List<Researcher> getProjectResearchers(int projectId);
}
