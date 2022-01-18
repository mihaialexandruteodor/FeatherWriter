package com.mihaialexandruteodor.FeatherWriter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping({"","/","/index"})
    public String home(Model model)
    {
        return "index";
    }

}
