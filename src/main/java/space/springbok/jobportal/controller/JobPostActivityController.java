package space.springbok.jobportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.springbok.jobportal.entity.JobPostActivity;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.services.JobPostActivityService;
import space.springbok.jobportal.services.UserService;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

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

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", JobPostActivity.builder().build());
        model.addAttribute("user", userService.getCurrentUserProfile());

        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {

        User user = userService.getCurrentUser();

        if (user != null) {
            jobPostActivity.setPostedById(user);
        }

        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard";
    }
}
