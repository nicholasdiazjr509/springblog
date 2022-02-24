package com.codeup.springblog;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class DiceController {

    public int userNumber;

    public int randomNumber(){
        return (int)(Math.random()*(6-1+1));
    }
//    the access point of url
    @GetMapping("/roll-dice")
    public String showDice(){
        return "roll-dice";//roll-dice.html reference
    }

@GetMapping("/roll-dice/{n}")
    public String joinDice(@PathVariable int n, Model model){
       userNumber = randomNumber();
       model.addAttribute("userNumber", userNumber);
       model.addAttribute("n", n);
       String message;
       if(n == userNumber) {
           model.addAttribute("message" , "Correct");
       }else{
          model.addAttribute ("message", "Incorrect");
       }
       model.addAttribute("falseOrTrue", "message");
        return "roll-dice";

//        int randomNumber = randomNumber.nextInt(6) + 1;
//        model.addAttribute("userNumber", "You have chosen " + userNumber);
//        model.addAttribute("randomNumber", "The correct answer was " + randomNumber);
//        if(userNumber == randomNumber) {
//            model.addAttribute("message", "You were right!");
//        }else{
//            model.addAttribute
//                    ("message", "You made the wrong guess!");
//        }
//        return "/roll-dice";
//    }
}
}