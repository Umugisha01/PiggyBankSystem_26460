package com.piggybank.repository;

import com.piggybank.model.Location;
import com.piggybank.model.Location.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByName(String name);

    Optional<Location> findByCode(String code);

    List<Location> findByType(LocationType type);

    List<Location> findByType(LocationType type, Sort sort);

    Page<Location> findByType(LocationType type, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    boolean existsByType(LocationType type);

    @Query("SELECT l FROM Location l WHERE l.parent.id = :parentId")
    List<Location> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT l FROM Location l WHERE l.parent IS NULL")
    List<Location> findProvinces();

    @Query("SELECT u.location FROM User u WHERE u.id = :userId")
    Optional<Location> findLocationByUserId(@Param("userId") Long userId);

    List<Location> findByNameContainingIgnoreCase(String name);

    long countByType(LocationType type);
}