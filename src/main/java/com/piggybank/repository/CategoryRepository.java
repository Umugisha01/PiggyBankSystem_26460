package com.piggybank.repository;

import com.piggybank.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    List<Category> findByNameContaining(String name, Sort sort);
}