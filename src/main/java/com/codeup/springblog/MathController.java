package com.codeup.springblog;
///add/3/and/4 	7
///subtract/3/from/10 	7
///multiply/4/and/5 	20
/////divide/6/by/3
//
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.css.Counter;

@Controller
public class MathController {
    @RequestMapping(path = "/add/{number}", method = RequestMethod.GET)
//    @RequestMapping(path = "/add/{num1}/and/{num2}", method = RequestMethod.GET)
    //red no method attached
    @ResponseBody
    //establish method
    public int addition(@PathVariable int num1, @PathVariable int num2){
        return num1 + num2;
    }

    public String add(@PathVariable int number) {
        return number + " plus 4 is " + (number + 4) + "!";

    }
    @RequestMapping(path = "/subtract/{number}", method = RequestMethod.GET)
    //red no method attached
    @ResponseBody
    //establish method
    public String subtract(@PathVariable int number) {
       int num = 10;
        return number + " minus 10 is " + (num - number) + "!";

    }
    @RequestMapping(path = "/multiply/{number}", method = RequestMethod.GET)
    //red no method attached
    @ResponseBody
    //establish method
    public String multiply(@PathVariable int number) {
        return number + " times 5 is " + (number * 5) + "!";

    }
    @RequestMapping(path = "/divide/{number}", method = RequestMethod.GET)
    //red no method attached
    @ResponseBody
    //establish method
    public String divide(@PathVariable int number) {
        return number + " divided by 3 is " + (number / 3) + "!";

    }
}
