package space.springbok.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.jobportal.entity.JobPostActivity;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {
}
