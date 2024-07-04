package space.springbok.jobportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            model.addAttribute("userName", currentUserName);
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }
}
