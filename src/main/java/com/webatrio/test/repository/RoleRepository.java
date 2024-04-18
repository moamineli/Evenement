package com.webatrio.test.repository;

import java.util.Optional;

import com.webatrio.test.models.ERole;
import com.webatrio.test.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
