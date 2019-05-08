package com.example.codefellowship.controllers;

import com.example.codefellowship.UserNotFoundException;
import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserAccountsController {
    @Autowired
    ApplicationUserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView performSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String bio
    ) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setBio(bio);

        userRepository.save(user);

        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("")
    public String getSplashPage() {
        return "splash";
    }

    @GetMapping("/users/{userId}")
    public String getUserInfo(@PathVariable long userId, Model model) {
        Optional<ApplicationUser> user = userRepository.findById(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());

            return "user-info";
        }

        throw new UserNotFoundException();
    }
}
