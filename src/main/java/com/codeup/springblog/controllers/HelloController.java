package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



//entire class will be controller
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "<h1>Hello from Springblog!</h1>" ;
    }



//    @GetMapping("/hello")
//    @ResponseBody
//    public String hello(@PathVariable String name, Model model) {
//        model.addAttribute("name", name);
//        return "hello";
//    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return "Hello, " + name + " !";
    }
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/join")
    public String showJoinForm(){
        return "join";
    }
    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model){
        model.addAttribute("cohort", "Hello! Welcome to " + cohort + "!");
        return "join";
    }

    //REQUEST MAPPING
    //unlike GET MAPPING
    @RequestMapping(path = "/increment/{number}", method = RequestMethod.GET)
    //red no method attached
    @ResponseBody
    //establish method
    public String increment(@PathVariable int number){
        return number + " plus " + (number + 1) + "! ";
        //increment 1 time
    }
    @GetMapping("/number/{num}")
    @ResponseBody
    public String displayNumber(@PathVariable int num){
        //must use the value of method to return the value of the integer as a string object.
        return String.valueOf(num);
    }
}
