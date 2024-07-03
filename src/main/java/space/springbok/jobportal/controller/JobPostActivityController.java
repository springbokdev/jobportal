package space.springbok.jobportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import space.springbok.jobportal.services.UserService;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String searchForJobs(Model model) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        return "dashboard";
    }
}
