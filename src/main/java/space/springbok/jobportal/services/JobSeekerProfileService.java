package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.JobSeekerProfile;
import space.springbok.jobportal.repository.JobSeekerProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public Optional<JobSeekerProfile> getOne(Integer id) {
        return jobSeekerProfileRepository.findById(id);
    }
}
