package com.example.codefellowship.controllers;

import com.example.codefellowship.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    PostRepository postRepo;

    @Autowired
    ApplicationUserRepository userRepo;

    private class PostDateComparator implements Comparator<Post> {

        @Override
        public int compare(Post postA, Post postB) {
            if (postA.getCreatedAt().equals(postB.getCreatedAt())) {
                return 0;
            }

            if (postA.getCreatedAt().before(postB.getCreatedAt())) {
                return 1;
            } else {
                return -1;
            }


        }
    }

    @GetMapping("")
    public String getFeed(@AuthenticationPrincipal ApplicationUser user,
                          Model model) {
        ApplicationUser openSessionUser = userRepo.getOne(user.getId());
        List<UserFollow> following = openSessionUser.getUsersFollowed();
        List<Post> posts = openSessionUser.getPosts();
        for (UserFollow follow : following) {
            ApplicationUser followTarget = userRepo.getOne(follow.getUserTo().getId());

            List<Post> followTargetPosts = followTarget.getPosts();
            posts.addAll(followTargetPosts);
        }

        // TODO: posts sorting via comparator
        posts.sort(new PostDateComparator());

        model.addAttribute("posts", posts);

        return "feed";
    }
}

