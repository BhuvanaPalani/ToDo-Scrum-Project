package se.jensenyh.todo_list.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.jensenyh.todo_list.todo.TodoRepository;

@Controller
public class HomeController {


    @GetMapping("/")
    public String getHomePage(Authentication auth, Model model) {

        if (auth == null) {
            model.addAttribute("loggedIn", false);
        } else {

            var username = auth.getName();


            model.addAttribute("loggedIn", true);
            model.addAttribute("username", username);
        }

        return "home";
    }

    @GetMapping("/auth-success")
    public String getLoginSuccess(OAuth2AuthenticationToken authentication) {

        var username = authentication.getName();
        var userId = authentication.getPrincipal().getAttribute("sub");

        // Här finns användaruppgifter i
        // OAuth2AuthenticationToken authentication

        return "redirect:/";
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model homePage) {
        // homePage.addAttribute("todos", todoRepository.findAll());
        return "checklist";
    }
}
