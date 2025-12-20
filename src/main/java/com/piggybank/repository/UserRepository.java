package com.piggybank.repository;

import com.piggybank.model.User;
import com.piggybank.model.User.UserRole;
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
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByRole(UserRole role, Sort sort);

    Page<User> findByRole(UserRole role, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.location.name = :provinceName")
    List<User> findByProvinceName(@Param("provinceName") String provinceName);

    @Query("SELECT u FROM User u WHERE u.location.code = :provinceCode")
    List<User> findByProvinceCode(@Param("provinceCode") String provinceCode);

    @Query("SELECT u FROM User u WHERE u.location.type = 'PROVINCE' AND u.location.name = :provinceName")
    Page<User> findUsersByProvinceName(@Param("provinceName") String provinceName, Pageable pageable);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<User> findByLocationNameContainingIgnoreCase(String locationName);

    long countByRole(UserRole role);

    long countByLocationName(String locationName);

    // Location filtering methods
    @Query("SELECT u FROM User u WHERE u.location.type = 'PROVINCE' AND u.location.name = :provinceName")
    List<User> findByLocationProvince(@Param("provinceName") String provinceName);

    @Query("SELECT u FROM User u WHERE u.location.type = 'DISTRICT' AND u.location.name = :districtName")
    List<User> findByLocationDistrict(@Param("districtName") String districtName);

    @Query("SELECT u FROM User u WHERE u.location.type = 'SECTOR' AND u.location.name = :sectorName")
    List<User> findByLocationSector(@Param("sectorName") String sectorName);

    @Query("SELECT u FROM User u WHERE u.location.type = 'CELL' AND u.location.name = :cellName")
    List<User> findByLocationCell(@Param("cellName") String cellName);

    @Query("SELECT u FROM User u WHERE u.location.type = 'VILLAGE' AND u.location.name = :villageName")
    List<User> findByLocationVillage(@Param("villageName") String villageName);

    // Location counting methods
    @Query("SELECT COUNT(u) FROM User u WHERE u.location.type = 'PROVINCE' AND u.location.name = :provinceName")
    long countByLocationProvince(@Param("provinceName") String provinceName);

    @Query("SELECT COUNT(u) FROM User u WHERE u.location.type = 'DISTRICT' AND u.location.name = :districtName")
    long countByLocationDistrict(@Param("districtName") String districtName);

    @Query("SELECT COUNT(u) FROM User u WHERE u.location.type = 'SECTOR' AND u.location.name = :sectorName")
    long countByLocationSector(@Param("sectorName") String sectorName);

    @Query("SELECT COUNT(u) FROM User u WHERE u.location.type = 'CELL' AND u.location.name = :cellName")
    long countByLocationCell(@Param("cellName") String cellName);

    @Query("SELECT COUNT(u) FROM User u WHERE u.location.type = 'VILLAGE' AND u.location.name = :villageName")
    long countByLocationVillage(@Param("villageName") String villageName);

    // Top districts by user count
    @Query("SELECT u.location.name, COUNT(u) FROM User u WHERE u.location.type = 'DISTRICT' GROUP BY u.location.name ORDER BY COUNT(u) DESC")
    List<Object[]> findTopDistrictsByUserCount();
}