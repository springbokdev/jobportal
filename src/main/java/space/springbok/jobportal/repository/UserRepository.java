package space.springbok.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.jobportal.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
