package ua.kalahgame.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kalahgame.domain.Player;
import ua.kalahgame.service.PlayerService;

import java.util.Objects;

/**
 * Created by a.lomako on 1/30/2017.
 */
@RestController
public class PlayerController {
    public PlayerController() {
    }


    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public String listPlayers(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("listPlayer", this.playerService.findAll());
        return "player";

    }

    private PlayerService playerService;

    /*    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public Player getPlayerById(@PathVariable Long id) {
            return playerService.findPlayerById(id);
        }

        @RequestMapping(value="/add", method=RequestMethod.POST)*/
    public ModelAndView addingPlayer(@ModelAttribute Player player) {

        ModelAndView modelAndView = new ModelAndView("home");
        playerService.addPlayer(player);
        String message = "Player was successfully added.";
        modelAndView.addObject("message", message);

        return modelAndView;
    }

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
}
