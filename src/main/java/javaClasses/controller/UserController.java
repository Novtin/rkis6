package javaClasses.controller;

import javaClasses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javaClasses.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profileGet(Model model, Principal principal){
        String username = principal.getName();
        User user = userService.findUserByLogin(username);
        model.addAttribute("user", user);
        return "/profile";
    }

    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("user", new User());
        return "/login";
    }


    @PostMapping("/login")
    public String loginPost(@ModelAttribute("user") @Validated User user,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }
        if (!userService.loginUser(user.getLogin(), user.getPassword())){
            return "/login";
        }

        return "redirect:/profile";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model){
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("user") @Validated User user,
                                   BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userService.saveUser(user)){
            model.addAttribute("errorMessage",
                    "Пользователь с таким логином уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

}
