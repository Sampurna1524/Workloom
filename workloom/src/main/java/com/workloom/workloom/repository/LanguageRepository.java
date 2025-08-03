package com.workloom.workloom.repository;

import com.workloom.workloom.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
