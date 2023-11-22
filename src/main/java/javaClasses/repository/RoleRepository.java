package javaClasses.repository;

import jakarta.transaction.Transactional;
import javaClasses.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    Role getRoleById(Integer id);
    @Transactional
    Role getRoleByName(String name);
}
