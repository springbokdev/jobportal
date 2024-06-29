package space.springbok.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.jobportal.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {
}
