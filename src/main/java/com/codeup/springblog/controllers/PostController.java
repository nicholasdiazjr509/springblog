package com.codeup.springblog.controllers;
//
////GET 	/posts 	posts index page
////GET 	/posts/{id} 	view an individual post
////GET 	/posts/create 	view the form for creating a post
////POST 	/posts/create 	create a new post
//
//
import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private UserRepository usersDao;
   private PostRepository postsDao;
    private final EmailService emailService;

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
        //Post newPost = new Post(title, body);
        newPost.setUser(usersDao.getById(1L));
        postsDao.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Post posttoEdit = postsDao.getById(id);
        model.addAttribute("postToEdit", posttoEdit);
        return "posts/edit";
    }
    // We can access the values submitted from the form using our @RequestParam annotation
    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post postToEdit, @PathVariable long id) {

        // grab the post from our DAO
//    Post postToEdit = postsDao.getById(id);
        // use setters to set new values to the object
//    postToEdit.setTitle(title);
//    postToEdit.setBody(body);
        // save the object with new values
        postsDao.save(postToEdit);
        return "redirect:/posts";
    }

    // For now, we need to use a GetMapping, that way, when we visit the page,
    // our app can access the path variable, then delete the post, then redirect
    // us back to the post index page.
    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }
    @GetMapping("/send-email")
    public String sendEmail(){
        emailService.prepareAndSend("Testing", "Hope it worked");
        return "redirect:/";
    }

}