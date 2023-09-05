package com.ldz.SpendWise.repository;

import com.ldz.SpendWise.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"userRoles"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"userRoles"})
    Page<User> findUsersBySearchKeyContains(String searchTerm, Pageable pageable);
}
