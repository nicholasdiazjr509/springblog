package com.codeup.springblog.controllers;
//
////GET 	/posts 	posts index page
////GET 	/posts/{id} 	view an individual post
////GET 	/posts/create 	view the form for creating a post
////POST 	/posts/create 	create a new post
//
//
import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
public class PostController {
    private UserRepository usersDao;
    private PostRepository postsDao;
    private EmailService emailService;

    public PostController(UserRepository usersDao,PostRepository postsDao, EmailService emailService) {
        this.usersDao = usersDao;
        this.postsDao = postsDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model) {
        model.addAttribute("allPosts", postsDao.findAll());

        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable long id, Model model) {
        model.addAttribute("singlePost", postsDao.getById(id));
        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newPost", new Post());
        return "posts/create";
    }


    @PostMapping("/posts/create")
    public String submitCreateForm(@ModelAttribute Post newPost) {
        newPost.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        emailService.prepareAndSend(newPost, "New post created", "You had posted to our blog!");
        postsDao.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Post postToEdit = postsDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (postToEdit.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("postToEdit", postToEdit);
            return "posts/edit";
        } else {
            return "redirect:/posts";
        }
    }

//
//        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(loggedInUser.getId() == postsDao.getById(id).getUser().getId()){
//            Post posttoEdit = postsDao.getById(id);
//            model.addAttribute("postToEdit", posttoEdit);
//            return "posts/edit";
//        }else {
//            return "redirect:/posts";
//        }
//        model.addAttribute("postToEdit", posttoEdit);
//        return "posts/edit";
//    }

    // We can access the values submitted from the form using our @RequestParam annotation
    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post postToEdit, @PathVariable long id) {
        if (postsDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            postToEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            // grab the post from our DAO
//    Post postToEdit = postsDao.getById(id);
            // use setters to set new values to the object
//    postToEdit.setTitle(title);
//    postToEdit.setBody(body);
            // save the object with new values
            postsDao.save(postToEdit);
        }
        return "redirect:/posts";

    }
//
    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }
}