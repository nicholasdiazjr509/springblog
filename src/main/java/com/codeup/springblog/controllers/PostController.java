package com.codeup.springblog.controllers;
//
////GET 	/posts 	posts index page
////GET 	/posts/{id} 	view an individual post
////GET 	/posts/create 	view the form for creating a post
////POST 	/posts/create 	create a new post
//
//
import com.codeup.springblog.models.Post;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class PostController {

    @GetMapping("/posts")
     //@ResponseBody
        public String index(Model model){
            List<Post> posts = new ArrayList<>();

            posts.add(new Post("One post", "First"));
            posts.add(new Post("Second post", "Second"));

            model.addAttribute("posts", posts);
            return "posts/index" ;
        }

        @GetMapping("/posts/{id}")
        public String postDetails(@PathVariable long id, Model model) {

            Post post = new Post("new title", "new body");
            model.addAttribute("postId", id);
            model.addAttribute("post", post);
            return "posts/show" ;
        }
        @GetMapping("/posts/create")
        @ResponseBody
            public String showCreateForm() {
                return "view the form for creating a post";
            }

        @PostMapping("/post/create")
        @ResponseBody
            public String submitCreateForm() {
            return "create a new post";
    }

}