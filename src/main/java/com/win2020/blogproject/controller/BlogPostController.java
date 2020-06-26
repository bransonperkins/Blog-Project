package com.win2020.blogproject.controller;

import com.win2020.blogproject.model.BlogPost;
import com.win2020.blogproject.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private static final List<BlogPost> posts = new ArrayList<>();
    private BlogPost blogPost;

    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {
        posts.removeAll(posts);
        for (BlogPost post : blogPostRepository.findAll()) {
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping(value = "/blogposts/new")
    public String newBlog(BlogPost blogPost) {
        return "new";
    }

    @PostMapping(value = "/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(blogPost);
        // Add new blog posts as they are created to our posts list for indexing
        posts.add(blogPost);

        // Add attributes to our model so we can show them to the user on results
        model.addAttribute("blogPost", blogPost);
        return "result";
    }

    // Similar to @PostMapping or @GetMapping, but allows for @PathVariable
    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    // Spring takes whatever value is in {id} and passes it to our method params using @PathVariable
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        // findById() returns an Optional<T> which can be null, so we have to test
        Optional<BlogPost> post = blogPostRepository.findById(id);
        // Test is post has anything in it
        if (post.isPresent()) {
            // Unwrap the post from Optional shell
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        }
        return "edit";
    }

    @RequestMapping(value = "/blogposts/update/{id}")
    public String updatePost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if(post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            // save works for saving new posts and overwriting existing posts
            blogPostRepository.save(actualPost);
            model.addAttribute(blogPost);
        }
        return "result";
    }

    @RequestMapping(value = "blogposts/delete/{id}")
    public String deletePostById(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "delete";
    }

}
