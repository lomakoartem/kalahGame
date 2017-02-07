package ua.kalahgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by a.lomako on 2/1/2017.
 */
@Controller
public class LinkNavigation {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

    @RequestMapping(value="/sec/player", method=RequestMethod.GET)
    public ModelAndView moderatorPage() {
        return new ModelAndView("player");
    }

    @RequestMapping(value="/admin/first", method=RequestMethod.GET)
    public ModelAndView firstAdminPage() {
        return new ModelAndView("admin-first");
    }

    @RequestMapping(value="/admin/second", method=RequestMethod.GET)
    public ModelAndView secondAdminPage() {
        return new ModelAndView("admin-second");
    }

}