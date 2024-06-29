package space.springbok.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.jobportal.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}
