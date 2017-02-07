package ua.kalahgame.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kalahgame.domain.Player;
import ua.kalahgame.service.PlayerService;


@Controller
public class HomeController {

    @Autowired
    private PlayerService userService;

    @RequestMapping("/register")
    public ModelAndView getRegisterForm(@ModelAttribute("user") Player user,
                                        BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("Register", "model", model);
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUserData(@ModelAttribute("user") Player user,
                                     BindingResult result) {
        userService.addPlayer(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/userList")
    public ModelAndView getUserList() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", userService.findAll());
        return new ModelAndView("UserDetails", model);
    }

    @RequestMapping("/getPlayerInfo")
    public ModelAndView getUserInformation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Player player = userService.findPlayerById(userService.getUserByLogin(auth.getName()));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("player", player);
        return new ModelAndView("UserDetail", model);
    }

    @RequestMapping(value = "/generatePassword")
    public ModelAndView generatePasswordForPlayer(String email) {
        Player player = userService.getUserByEmail(email);
        userService.generatePasswordAndSendByMail(player.getId());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editplayer/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Player player = userService.findPlayerById(id);
        return new ModelAndView("playereditform", "command", player);
    }

    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public ModelAndView editsave(@ModelAttribute("player") Player player) {
        userService.updatePlayer(player);
        return new ModelAndView("redirect:/userList");
    }

    @RequestMapping(value = "/deleteplayer/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id) {
        userService.deletePlayer(id);
        return new ModelAndView("redirect:/userList");
    }
}
