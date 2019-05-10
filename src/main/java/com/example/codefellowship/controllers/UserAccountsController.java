package com.example.codefellowship.controllers;

import com.example.codefellowship.UserNotFoundException;
import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.ApplicationUserRepository;
import com.example.codefellowship.database.PostRepository;
import com.example.codefellowship.database.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserAccountsController {
    @Autowired
    ApplicationUserRepository userRepository;

    @Autowired
    PostRepository postRepo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignup(@AuthenticationPrincipal ApplicationUser user) {
        if (user != null) {
            return "redirect:/feed";
        }

        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView performSignup(
            @AuthenticationPrincipal ApplicationUser currentUser,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String bio
    ) {
        if (currentUser != null) {
            return new RedirectView("/feed");
        }

        ApplicationUser testCase = userRepository.findByUsername(username);
        if (testCase != null) {
            return new RedirectView("/login");
        }

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
    public String getLoginPage(@AuthenticationPrincipal ApplicationUser user) {
        if (user != null) {
            return "redirect:/feed";
        }
        return "login";
    }

    @GetMapping("")
    public String getSplashPage(@AuthenticationPrincipal ApplicationUser user, Model model) {
        if (user != null) {
            return "redirect:/feed";
        }

        model.addAttribute("user", user);

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

    @GetMapping("/userpanel")
    public String getUserPanel(@AuthenticationPrincipal ApplicationUser user, Model model) {
        ApplicationUser intermediary = userRepository.findByUsername(user.getUsername());
        List<UserFollow> intermediaryFollows = intermediary.getUsersFollowed();


        List<ApplicationUser> otherUsers = userRepository.findAll();
        otherUsers.remove(intermediary);
        otherUsers.removeAll(intermediary.getUsersFollowed());

        for (UserFollow follow : intermediaryFollows) {
            otherUsers.remove(follow.getUserTo());
        }

        model.addAttribute("otherUsers", otherUsers);
        model.addAttribute("user", intermediary);
//        List posts = user.getPosts();
        model.addAttribute("posts", intermediary.getPosts());

        return "userPanel";
    }
}
