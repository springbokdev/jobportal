package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.JobPostActivity;
import space.springbok.jobportal.repository.JobPostActivityRepository;

@Service
@RequiredArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
