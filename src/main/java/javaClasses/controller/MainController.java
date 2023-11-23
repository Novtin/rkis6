package javaClasses.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import javaClasses.entity.Glasses;
import javaClasses.entity.User;
import javaClasses.repository.GlassesRepository;
import javaClasses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class MainController {
    private final GlassesRepository glassesRepository;
    private final UserService userService;

    @Autowired
    public MainController(GlassesRepository glassesRepository, UserService userService) {
        this.glassesRepository = glassesRepository;
        this.userService = userService;
    }

    @GetMapping("/menu")
    public String showMenu(Authentication authentication, Model model) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                model.addAttribute("role", role);
                break;
            } else if ("ROLE_USER".equals(role)) {
                model.addAttribute("role", role);
            }
        }
        return "menu";
    }

    @GetMapping("/table")
    public String showTable(Model model) {
        model.addAttribute("glassesList", glassesRepository.findAllByOrderById());
        return "table";
    }

    @GetMapping("/save")
    public String addElementGet(Model model) {
        model.addAttribute("glasses", new Glasses());
        return "save";
    }

    @PostMapping("/save")
    public String addElementPost(@Valid @ModelAttribute("glasses") Glasses glasses,
                                 BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Введите корректные данные");
            return "save";
        }
        glassesRepository.save(glasses);
        return "redirect:/menu";
    }

    @GetMapping("/delete")
    public String deleteElementGet(Model model) {
        model.addAttribute("glassesList", glassesRepository.findAllByOrderById());
        model.addAttribute("func", Arrays.asList("delete", "удаления"));
        return "inputId";
    }

    @PostMapping("/delete")
    public String deleteElementPost(@RequestParam(value = "inputId")
                                        @NotBlank @Pattern(regexp = "\\d+") String deleteId,
                                    Model model) {
        try {
            Optional<Glasses> glasses = glassesRepository.findById(Long.parseLong(deleteId));
            if (glasses.isEmpty()){
                throw new IndexOutOfBoundsException();
            } else {
                glasses.ifPresent(glassesRepository::delete);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException exception){
            model.addAttribute("errorMessage", "Введите корректный id");
            model.addAttribute("glassesList",  glassesRepository.findAllByOrderById());
            model.addAttribute("func", Arrays.asList("delete", "удаления"));
            return "inputId";
        }
        return "redirect:/menu";
    }

    @GetMapping("/find")
    public String findByDioptersGet() {
        return "find";
    }

    @PostMapping("/find")
    public String findByDioptersPost(@RequestParam(value = "inputDiopters")
                                         @NotBlank @Pattern(regexp = "^-?\\d+(\\.\\d+)?$") String inputDiopters,
                                     Model model) {
        double diopters;
        try {
            diopters = Double.parseDouble(inputDiopters);
        } catch (NumberFormatException exception){
            model.addAttribute("errorMessage", "Введите корректный id");
            model.addAttribute("glassesList",  glassesRepository.findAllByOrderById());
            return "find";
        }
        model.addAttribute("glassesList",
                glassesRepository.findGlassesByDioptersGreaterThan(diopters));
        return "table";
    }

    @GetMapping("/inputEdit")
    public String inputEditGet(Model model) {
        model.addAttribute("glassesList",  glassesRepository.findAllByOrderById());
        model.addAttribute("func", Arrays.asList("inputEdit", "изменения"));
        return "inputId";
    }

    @PostMapping("/inputEdit")
    public String inputEditPost(Model model, @RequestParam(value = "inputId")
    @NotBlank @Pattern(regexp = "\\d+") String editId) {
        try {
            Optional<Glasses> glasses = glassesRepository.findById(Long.parseLong(editId));
            if (glasses.isEmpty()){
                throw new IndexOutOfBoundsException();
            } else {
                model.addAttribute("glasses",  glasses.get());
            }
        } catch (NumberFormatException | IndexOutOfBoundsException exception){
            model.addAttribute("errorMessage", "Введите корректный id");
            model.addAttribute("glassesList",  glassesRepository.findAllByOrderById());
            model.addAttribute("func", Arrays.asList("inputEdit", "изменения"));
            return "inputId";
        }
        return "edit";
    }

    @GetMapping("/edit")
    public String editElementGet(Model model, @ModelAttribute("glasses") Glasses glasses) {
        model.addAttribute("glasses",  glasses);
        return "edit";
    }

    @PostMapping("/edit")
    public String editElementPost(Model model, @Valid @ModelAttribute("glasses") Glasses glasses,
                                  BindingResult result){
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Введите корректные данные");
            model.addAttribute("glasses", glasses);
            return "edit";
        }
        glassesRepository.save(glasses);
        return "redirect:/menu";
    }

    @GetMapping("/profile")
    public String profileGet(Model model, Principal principal){
        String username = principal.getName();
        User user = userService.findUserByLogin(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("user", new User());
        return "login";
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
        return "registration";
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
