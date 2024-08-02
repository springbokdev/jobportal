package space.springbok.jobportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import space.springbok.jobportal.entity.JobPostActivity;
import space.springbok.jobportal.entity.RecruiterJobDto;
import space.springbok.jobportal.entity.RecruiterProfile;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.services.JobPostActivityService;
import space.springbok.jobportal.services.UserService;

import java.util.Date;
import java.util.List;

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

            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                List<RecruiterJobDto> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
                model.addAttribute("jobPost", recruiterJobs);
            }

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

    @PostMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable int id, Model model) {

        JobPostActivity jobPostActivity =  jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }


}
