package space.springbok.jobportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.springbok.jobportal.entity.UserType;
import space.springbok.jobportal.repository.UserTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public List<UserType> getAll() {
        return userTypeRepository.findAll();
    }

}
