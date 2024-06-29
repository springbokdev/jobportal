package space.springbok.jobportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.springbok.jobportal.entity.User;
import space.springbok.jobportal.entity.UserType;
import space.springbok.jobportal.services.UserService;
import space.springbok.jobportal.services.UserTypeService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserTypeService userTypeService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user", User.builder().build());
        return "register";
    }

    @PostMapping("register/new")
    public String userRegistration(@Valid User user, Model model) {
        log.debug(user.toString());

        Optional<User> optionalUser = userService.getUserByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            model.addAttribute("error", "Email already registered, try to login or register with other email.");
            List<UserType> userTypes = userTypeService.getAll();
            model.addAttribute("getAllTypes", userTypes);
            model.addAttribute("user", User.builder().build());
            return "register";
        }

        userService.addNew(user);
        return "dashboard";
    }

}
