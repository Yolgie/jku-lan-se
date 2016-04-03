package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.events.tournament.Tournament;
import at.jku.oeh.lan.laganizer.model.events.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.io.Serializable;

@RestController
@RequestMapping("/tournaments/")
@PermitAll
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    //C    create
    // RU  disable/enable
    // RU  start
    // RU  finish
    // RU  name
    // RU  game
    //CRUD team / teams

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO<>((Serializable) tournamentService.findAllTournaments(), true);
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Tournament> create(@RequestParam String name,
                                                 @RequestParam long eventManagerUserId,
                                                 @RequestParam String startTime,
                                                 @RequestParam String description,
                                                 @RequestParam int maxTeamSize,
                                                 @RequestParam String game) {
        RESTDataWrapperDTO<Tournament> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(tournamentService.createTournament(game, maxTeamSize, eventManagerUserId, startTime, description, game));
            result.setSuccess(true);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("Event manager with user ID " + eventManagerUserId + " can't be found");
            result.setSuccess(false);
        }
        return result;
    }
}
