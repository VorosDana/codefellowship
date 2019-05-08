package com.example.codefellowship.controllers;

import com.example.codefellowship.database.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class Posts {
    @Autowired
    PostRepository postRepo;

    @GetMapping("/{postId}")
    public String getPostDetails(@PathVariable long postId,
                                 Model model) {
        return "postDetails";
    }
}
