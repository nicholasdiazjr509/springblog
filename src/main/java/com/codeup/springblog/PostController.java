package com.codeup.springblog;
//
////GET 	/posts 	posts index page
////GET 	/posts/{id} 	view an individual post
////GET 	/posts/create 	view the form for creating a post
////POST 	/posts/create 	create a new post
//
//
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {


        @GetMapping("/posts")
        @ResponseBody
        public String viewPosts(){
            return "posts index page";
        }

        @GetMapping("/posts/{id}")
        @ResponseBody
        public String postDetails(@PathVariable long id) {
            return "view an individual post" ;
        }
        @GetMapping("/posts/create")
        @ResponseBody
            public String showCreateForm(){
                    return "view the form for individual post";
        }
        @PostMapping("/post/create")
            @ResponseBody
            public String submitCreateForm() {
            return "create a new post";
    }
}