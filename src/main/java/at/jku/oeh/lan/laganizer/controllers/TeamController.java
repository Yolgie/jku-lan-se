package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.events.tournament.TournamentNotFoundException;
import at.jku.oeh.lan.laganizer.model.events.tournament.team.InvalidTeamnameException;
import at.jku.oeh.lan.laganizer.model.events.tournament.team.Team;
import at.jku.oeh.lan.laganizer.model.events.tournament.team.TeamNotFoundException;
import at.jku.oeh.lan.laganizer.model.events.tournament.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> create(@RequestParam long userId, @RequestParam long tournamentId) {
        RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();

        try {
            result.setData(teamService.createTeam(userId, tournamentId));
            result.setSuccess(false);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorDetails("User can't be found");
        } catch (TournamentNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorDetails("Tournament can't be found");
        }
        return result;
    }

    @RequestMapping(value = "addPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> addPlayer(@RequestParam long teamId, @RequestParam long userId) {
        RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(teamService.addPlayer(teamId, userId));
            result.setSuccess(true);
        } catch (TeamNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("Team with ID " + teamId + " can't be found");
            result.setSuccess(false);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("User with ID " + userId + " can't be found");
            result.setSuccess(true);
        }
        return result;
    }

    @RequestMapping(value = "delPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> delPlayer(@RequestParam long teamId, @RequestParam long userId) {
        RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(teamService.removePlayer(teamId, userId));
            result.setSuccess(true);
        } catch (TeamNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("Team with ID " + teamId + " can't be found");
            result.setSuccess(false);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("User with ID " + userId + " can't be found");
            result.setSuccess(true);
        }
        return result;
    }

    @RequestMapping(value = "changeName", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> changeName(@RequestParam long id, @RequestParam String name) {
        RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(teamService.renameTeam(id, name));
            result.setSuccess(true);
        } catch (TeamNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("Team with ID " + id + " can't be found");
            result.setSuccess(false);
        } catch (InvalidTeamnameException e) {
            e.printStackTrace();
            result.setErrorDetails("Team can't be renamed to " + name + ". Name not Valid");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    public RESTDataWrapperDTO<Team> getTeam(@RequestParam long id) {
        RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(teamService.findTeamById(id));
            result.setSuccess(true);
        } catch (TeamNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("Team with Id " + id + " can't be found");
            result.setSuccess(false);
        }
        return result;
    }
}
