package by.epamlab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Home {

    @RequestMapping(value = {"/","/home"})
    public String index(){
        return "/start.jsp";
    }
}
