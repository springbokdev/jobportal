package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.JobSeekerProfile;
import space.springbok.jobportal.entity.RecruiterProfile;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.repository.JobSeekerProfileRepository;
import space.springbok.jobportal.repository.RecruiterProfileRepository;
import space.springbok.jobportal.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    public User addNew(User user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        User savedUser = userRepository.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(RecruiterProfile.builder()
                            .userId(savedUser)
                    .build());
        } else {
            jobSeekerProfileRepository.save(JobSeekerProfile.builder()
                            .userId(savedUser)
                    .build());
        }

        return savedUser;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
