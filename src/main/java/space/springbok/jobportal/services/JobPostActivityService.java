package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.*;
import space.springbok.jobportal.repository.JobPostActivityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobDto> getRecruiterJobs(int recruiterId) {
        List<IRecruiterJob>  recruiterJobDtos = jobPostActivityRepository.getRecruiterJobs(recruiterId);

        List<RecruiterJobDto> recruiterJobDtoList = new ArrayList<>();

        for (IRecruiterJob rec: recruiterJobDtos) {
             JobLocation loc = JobLocation.builder()
                     .id(rec.getLocationId())
                     .city(rec.getCity())
                     .state(rec.getState())
                     .country(rec.getCountry())
                     .build();
            JobCompany jobCompany = JobCompany.builder()
                    .id(rec.getCompanyId())
                    .name(rec.getName())
                    .logo("")
                    .build();
            recruiterJobDtoList.add(RecruiterJobDto.builder()
                            .totalCandidates(rec.getTotalCandidates())
                            .jobPostId(rec.getJob_post_id())
                            .jobTitle(rec.getJob_title())
                            .jobLocationId(loc)
                            .jobCompanyId(jobCompany)
                    .build());
        }

        return recruiterJobDtoList;
    }


    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found!"));
    }
}
