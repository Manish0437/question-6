/*
 * You can use the following import statements
 *
 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 * 
 */

// Write your code here

package com.example.findmyproject.repository;

import com.example.findmyproject.model.Project;
import com.example.findmyproject.model.Researcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearcherJpaRepository extends JpaRepository<Researcher, Integer> {
    // List<Project> findByEvent(Event event);
}
