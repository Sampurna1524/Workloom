package com.workloom.workloom.repository;

import com.workloom.workloom.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
