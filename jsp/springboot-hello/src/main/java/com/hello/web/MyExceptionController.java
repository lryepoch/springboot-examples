package com.hello.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyExceptionController {

    @RequestMapping(value = "my")
    public String myexception(Model model) throws Exception {

        model.addAttribute("message", "You will never walk alone!");

        if (true) {
            throw new Exception("some exception!");
        }

        return "success";
    }
}
