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

//    @GetMapping("/posts")
//    public String viewPosts(Model model) {
//        List<Post> allPosts = new ArrayList<>();
//        Post p2 = new Post(2, "Test", "This is for testing purposes");
//        Post p3 = new Post(3, "Weather Update", "It's gon rain");
//        Post p4 = new Post(4, "Codeup", "Join codeup today and get your career launched in tech!");
//
//        allPosts.add(p2);
//        allPosts.add(p3);
//        allPosts.add(p4);
//
//        model.addAttribute("allPosts", allPosts);
//
//        return "posts/index";
//    }

    @GetMapping("/posts")
    public String viewPosts(Model model) {
        model.addAttribute("allPosts", postsDao.findAll());
        return "posts/index";
    }

//    changed this for security exercise
    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable long id, Model model, Principal principal) {
        String username = "";
        if(principal != null){
            username = principal.getName();
        }
        model.addAttribute("username", username);
        model.addAttribute("singlePost", postsDao.getById(id));
        return "posts/show";
    }

    //    changed this for authentication exercise
    @GetMapping("/posts/create")
    public String showCreateForm() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("newPost", new Post());
        if(loggedInUser != null){
        return "posts/create";
    }else{
        return "redirect:/login";
    }
}
    @PostMapping("/posts/create")
    public String submitCreateForm(@RequestParam String title, @RequestParam String body) {
        Post newPost = new Post();
//        newPost.setUser(usersDao.getById(1L));
        newPost.setTitle(title);
        newPost.setBody(body);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(loggedInUser);
        emailService.prepareAndSend(newPost, "Created new post", "Did this work?");
        postsDao.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedInUser.getId() == postsDao.getById(id).getUser().getId()){
            Post posttoEdit = postsDao.getById(id);
            model.addAttribute("postToEdit", posttoEdit);
            return "posts/edit";
        }else {
            return "redirect:/posts";
        }
//        model.addAttribute("postToEdit", posttoEdit);
//        return "posts/edit";
    }
    // We can access the values submitted from the form using our @RequestParam annotation
    @PostMapping("/posts/{id}/edit")
//We can access the values submitted from the form using our @Requestparam annotation
    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post postToEdit, @PathVariable long id) {
//        Post postToEdit = postsDao.getById(id);
//        postToEdit.setTitle(title);
//        postToEdit.setBody(body);
//
        postsDao.save(postToEdit);
        return "redirect:/posts";
    }
//
    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }
}