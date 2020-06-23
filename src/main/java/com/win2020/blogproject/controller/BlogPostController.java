package com.win2020.blogproject.controller;

import com.win2020.blogproject.model.BlogPost;
import com.win2020.blogproject.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping
    public String index(BlogPost blogPost, Model model) {
        model.addAttribute("posts", posts);
        return "index";
    }

    private BlogPost blogPost;

    @PostMapping
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "result";
    }

}