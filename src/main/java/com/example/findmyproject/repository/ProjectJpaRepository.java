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
public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    // List<Event> findBySponsor(Sponsor sponsor);
}
