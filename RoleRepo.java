package blogapp_api.Repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import blogapp_api.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
