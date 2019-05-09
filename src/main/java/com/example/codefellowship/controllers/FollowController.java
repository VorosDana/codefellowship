package com.example.codefellowship.controllers;

import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.ApplicationUserRepository;
import com.example.codefellowship.database.UserFollow;
import com.example.codefellowship.database.UserFollowDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    ApplicationUserRepository userRepo;

    @Autowired
    UserFollowDatabase followRepo;

    @PostMapping("/")
    public RedirectView followUser(@AuthenticationPrincipal ApplicationUser user,
                                   @RequestParam String followTargetName,
                                   Model model) {
        ApplicationUser openSessionUser = userRepo.findByUsername(user.getUsername());
        ApplicationUser followTarget = userRepo.findByUsername(followTargetName);

        if (!openSessionUser.getUsersFollowed().contains(followTarget)) {
            UserFollow follow = new UserFollow();
            follow.setUserFrom(openSessionUser);
            follow.setUserTo(followTarget);

            followRepo.save(follow);
        }

        return new RedirectView("/users/" + followTarget.getId());
    }
}
