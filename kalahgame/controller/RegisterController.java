//package ua.kalahgame.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import ua.kalahgame.domain.Player;
//import ua.kalahgame.service.PlayerService;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//@Controller
//@RequestMapping("/")
//public class RegisterController {
//    @Autowired
//    private PlayerService playerService;
//
///*
//@Autowired
//    public RegisterController(PlayerService playerService) {
//        this.playerService = playerService;
//    }
//*/
//
//
//    @RequestMapping("/welcome")
//    public ModelAndView getRegisterForm(@ModelAttribute("player") Player player,
//                                        BindingResult result) {
//
//        ArrayList<String> gender = new ArrayList<String>();
//        gender.add("Male");
//        gender.add("Female");
//
//        ArrayList<String> city = new ArrayList<String>();
//        city.add("Delhi");
//        city.add("Kolkata");
//        city.add("Chennai");
//        city.add("Bangalore");
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("gender", gender);
//        model.put("city", city);
//
//        System.out.println("Register Form");
//        return new ModelAndView("Register", "model", model);
//
//    }
//
//    @RequestMapping("/saveUser")
//    public ModelAndView saveUserData(@ModelAttribute("player") Player player,
//                                     BindingResult result) {
//
//        playerService.addPlayer(player);
//        System.out.println("Save User Data");
//        return new ModelAndView("redirect:/userList.html");
//    }
//
//    @RequestMapping("/userList")
//    public ModelAndView getPlayerList() {
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("player", playerService.findAll());
//        return new ModelAndView("UserDetails", model);
//    }
//
//
//}
