package tr.com.mse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mse.model.UserLog;

@Repository("UserLogRepository")
public interface UserLogRepository extends JpaRepository<UserLog, String> {

}