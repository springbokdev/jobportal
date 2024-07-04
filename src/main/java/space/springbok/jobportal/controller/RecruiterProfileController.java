package space.springbok.jobportal.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.springbok.jobportal.entity.RecruiterProfile;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.repository.UserRepository;
import space.springbok.jobportal.services.RecruiterProfileService;
import space.springbok.jobportal.services.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {

    private final UserService userService;
    private final RecruiterProfileService recruiterProfileService;

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            User user = userService.getUserByEmail(currentUsername).orElseThrow(
                    () -> new UsernameNotFoundException("Could not find user"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());

            if (recruiterProfile.isPresent()) {
                model.addAttribute("profile", recruiterProfile.get());
            }
        }

        return "recruiter-profile";
    }
}
