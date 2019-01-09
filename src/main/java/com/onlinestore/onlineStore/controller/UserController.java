package com.onlinestore.onlineStore.controller;

import com.onlinestore.onlineStore.model.Roles;
import com.onlinestore.onlineStore.model.User;
import com.onlinestore.onlineStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String openLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String openRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitRegisterUserForm(@Valid User user, BindingResult bindingResult, @RequestParam(value = "confirmPassword") String confirmPassword, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordNotMatching", "Passwords doesn't match");
            return "register";
        }
        user.setRoles(Roles.valueOf("SITE_USER"));
        userService.save(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String openLoginPageWithError(Model model) {
        model.addAttribute("loginError", "Incorrect username or password. Try again!");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
