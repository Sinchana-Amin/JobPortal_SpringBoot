package com.sinchana.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("onAuthenticationSuccess method is being executed");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("The username " + username + " is logged in.");
        boolean hasJobSeekerRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("Job Seeker"));
        boolean hasRecruiterRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("Recruiter"));

        /*boolean hasJobSeekerRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equalsIgnoreCase("Job Seeker") || r.getAuthority().equalsIgnoreCase("ROLE_JobSeeker"));
        boolean hasRecruiterRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equalsIgnoreCase("Recruiter") || r.getAuthority().equalsIgnoreCase("ROLE_Recruiter"));

*/
        if (hasJobSeekerRole || hasRecruiterRole) {
            System.out.println("Login successful, redirecting to /dashboard/");
            response.sendRedirect("/dashboard/");
        } else {
            System.out.println("User does not have expected roles. Redirecting to home.");
            response.sendRedirect("/"); // Fallback if no role matches
        }
    }
}