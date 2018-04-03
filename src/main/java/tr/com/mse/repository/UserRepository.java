package tr.com.mse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mse.model.User;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String> {

	User findByUserCode(String userCode);
}