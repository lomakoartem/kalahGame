package ua.kalahgame.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        //  System.out.println(userService.findPlayerByLogin("test"));
              Map<String, Object> model = new HashMap<String, Object>();
        System.out.println("Register Form");


        return new ModelAndView("Register", "model", model);
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUserData(@ModelAttribute("user") Player user,
                                     BindingResult result) {
        userService.addPlayer(user);
        System.out.println("Save User Data");

        return new ModelAndView("redirect:/index.html");
    }

    @RequestMapping("/userList")
    public ModelAndView getUserList() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", userService.findAll());
        return new ModelAndView("UserDetails", model);

    }
    @RequestMapping("/getPlayerInfo")
    public ModelAndView getUserInformation(Long id) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", userService.findPlayerById(id));
        return new ModelAndView("UserDetail", model);

    }
    @RequestMapping(value="/loggedUser", method = RequestMethod.GET)
    public String printUser(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        model.addAttribute("username", name);
        return "hello";
    }

 /*   @RequestMapping(value = "/generatePassword/{id}", method = RequestMethod.PUT)
    public void generatePasswordForVendor(*//*@PathVariable Long id, @Validated @RequestBody VendorDTO vendorDTO,
                                          BindingResult bindingResult*//*) {

        return userService.generatePasswordAndSendByMail(player.getId());
    }*/
}
