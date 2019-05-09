package com.example.codefellowship.controllers;

import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.Post;
import com.example.codefellowship.database.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping("/posts")
public class Posts {
    @Autowired
    PostRepository postRepo;

    @GetMapping("/{postId}")
    public String getPostDetails(@PathVariable long postId,
                                 Model model) {
        Post post = postRepo.getOne(postId);
        model.addAttribute("post", post);

        return "postDetails";
    }

    @PostMapping("")
    public RedirectView createPost(@RequestParam String body,
                                   @AuthenticationPrincipal ApplicationUser poster) {
        Post post = new Post();
        post.setBody(body);
        post.setPoster(poster);

        post.setCreatedAt(new Date());
        post = postRepo.save(post);

        return new RedirectView("/feed");
    }

    @DeleteMapping("/{postId}")
    public RedirectView deletePost(@PathVariable long postId,
                                   @AuthenticationPrincipal ApplicationUser user) {
        Post post = postRepo.getOne(postId);
        RedirectView redirect = new RedirectView("/feed");
        if (!post.getPoster().equals(user)) {
            redirect.setStatusCode(HttpStatus.FORBIDDEN);
            redirect.setUrl("/");
            return redirect;
        }
        postRepo.delete(post);
        return redirect;
    }
}
