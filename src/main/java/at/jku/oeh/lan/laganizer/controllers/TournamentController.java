package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.Tournament;
import at.jku.oeh.lan.laganizer.model.TournamentDAO;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.UserDAO;
import at.jku.oeh.lan.laganizer.services.EventService;
import at.jku.oeh.lan.laganizer.services.TimeService;
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
    private TournamentDAO tournamentDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventService eventService;
    @Autowired
    private TimeService timeService;

    //C    create
    // RU  disable/enable
    // RU  start
    // RU  finish
    // RU  name
    // RU  game
    //CRUD team / teams

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO<>((Serializable) tournamentDAO.findAll(), true);
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Tournament> create(@RequestParam String name,
                                                 @RequestParam long eventManagerUserId,
                                                 @RequestParam String startTime,
                                                 @RequestParam String description,
                                                 @RequestParam int teamSize,
                                                 @RequestParam String game) {
        RESTDataWrapperDTO<Tournament> tournamentRESTDataWrapperDTO = new RESTDataWrapperDTO<>();
        Tournament tournament = new Tournament();
        User tournamentManager = userDAO.findOne(eventManagerUserId);
        tournament.setName(name);
        tournament.setEventManager(tournamentManager);
        tournament.setStartTime(timeService.StringToInstant(startTime));
        tournament.setDescription(description);
        tournament.setEnabled(false);
        tournament.setTeamSize(teamSize);
        tournament.setGame(game);
        tournamentDAO.save(tournament);
        eventService.createEvent(tournament, tournamentManager);

        tournamentRESTDataWrapperDTO.setData(tournament);
        tournamentRESTDataWrapperDTO.setSuccess(true);
        return tournamentRESTDataWrapperDTO;
    }
}
