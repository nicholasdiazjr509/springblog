package com.codeup.springblog;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Welcome!";
//pass data using a "model" for the template
}
        @GetMapping("/quote-of-the-day/by/{author}")
                public String quote(@PathVariable String author, Model model){
//            pass info to our views, similar to rs
//            its reference
            model.addAttribute("author", author );
            return "quotes";
        }
    }

