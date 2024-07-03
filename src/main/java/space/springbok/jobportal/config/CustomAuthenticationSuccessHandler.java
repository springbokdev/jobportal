package space.springbok.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();
        log.info("The username {} is logged in.", userName);
        boolean hasJobSeekerRole = authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("Job Seeker"));
        boolean hasRecruiterRole = authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("Recruiter"));

        if (hasJobSeekerRole || hasRecruiterRole) {
            response.sendRedirect("/dashboard");
        }
    }
}
