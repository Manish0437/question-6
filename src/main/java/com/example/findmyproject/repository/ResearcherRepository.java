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

public interface ResearcherRepository {
    ArrayList<Researcher> getResearchers();

    Researcher getResearcherById(int researcherId);

    Researcher addResearcher(Researcher researcher);

    Researcher updateResearcher(int researcherId, Researcher researcher);

    void deleteResearcher(int researcherId);

    List<Project> getResearcherProjects(int researcherId);
}
