package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.RecruiterProfile;
import space.springbok.jobportal.repository.RecruiterProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public Optional<RecruiterProfile> getOne(Integer id) {
        return recruiterProfileRepository.findById(id);
    }
}
