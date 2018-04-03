package tr.com.mse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mse.enums.AuthorityType;
import tr.com.mse.model.Authority;

@Repository("RoleRepository")
public interface AuthorityRepository extends JpaRepository<Authority, String> {

	Authority findByName(AuthorityType roleName);
}