package ua.kalahgame.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kalahgame.domain.Player;
import ua.kalahgame.infrastructure.exceptions.EntityNotFoundException;
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

      if(userService.getUserByEmail(user.getEmail())!=null ||
              userService.getUserByLogin(user.getPlayer_login())!=null)
      {
          throw  new UsernameNotFoundException("User with this credentials already exists");
      }
        userService.addPlayer(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/admin/userList")
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
       if(player.getId()==null) throw  new UsernameNotFoundException("User not in db");
        userService.generatePasswordAndSendByMail(player.getId());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/admin/editplayer/{id}")
    public ModelAndView editplayer(@PathVariable Long id) {
        Player player = userService.findPlayerById(id);
        return new ModelAndView("playereditform", "command", player);
    }

    @RequestMapping(value = "/admin/editsave", method = RequestMethod.POST)
    public ModelAndView editsave(@ModelAttribute("player") Player player) {
        userService.updatePlayer(player);
        return new ModelAndView("redirect:/admin/userList");
    }

    @RequestMapping(value = "/admin/deleteplayer/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id) {
        userService.deletePlayer(id);
        return new ModelAndView("redirect:/admin /userList");
    }
    @ExceptionHandler({UsernameNotFoundException.class, java.sql.SQLException.class, EntityNotFoundException.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("error");

        model.addObject("exception", ex.getMessage());

        return model;
    }
}
