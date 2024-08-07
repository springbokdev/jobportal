package space.springbok.jobportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import space.springbok.jobportal.entity.JobSeekerProfile;
import space.springbok.jobportal.entity.Skill;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.repository.UserRepository;
import space.springbok.jobportal.services.JobSeekerProfileService;
import space.springbok.jobportal.util.FileUploadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
@RequiredArgsConstructor
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;

    private final UserRepository userRepository;

    @GetMapping("/")
    public String jobSeekerProfile(Model model) {
        JobSeekerProfile jobSeekerProfile = JobSeekerProfile.builder().build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skill> skills = new ArrayList<>();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()
            -> new UsernameNotFoundException("User not found"));

            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            if (seekerProfile.isPresent()) {
                jobSeekerProfile = seekerProfile.get();
                if (jobSeekerProfile.getSkills().isEmpty()) {
                    skills.add(new Skill());
                    jobSeekerProfile.setSkills(skills);
                }
            }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }


    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile,
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()
                    -> new UsernameNotFoundException("User not found"));

            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }

        List<Skill> skillsList= new ArrayList<>();
        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillsList);

        for (Skill skill : jobSeekerProfile.getSkills()) {
            skill.setJobSeekerProfile(jobSeekerProfile);
        }
        String imageName = "";
        String resumeName = "";

        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);

        try {
            String uploadDir = "photos/candidate/" + jobSeekerProfile.getUserAccountId();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }

            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "redirect:/dashboard";
    }

}
