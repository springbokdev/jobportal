package space.springbok.jobportal.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import space.springbok.jobportal.entity.RecruiterProfile;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.services.RecruiterProfileService;
import space.springbok.jobportal.services.UserService;
import space.springbok.jobportal.util.FileUploadUtil;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            User user = userService.getUserByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";

        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }

        RecruiterProfile savedRecruiterProfile = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedRecruiterProfile.getUserAccountId();

        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return "redirect:/dashboard";
    }
}
