package at.jku.oeh.lan.laganizer.model.events.tournament.team;

import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.base.UserService;
import at.jku.oeh.lan.laganizer.model.dao.TeamDAO;
import at.jku.oeh.lan.laganizer.model.events.tournament.Tournament;
import at.jku.oeh.lan.laganizer.model.events.tournament.TournamentNotFoundException;
import at.jku.oeh.lan.laganizer.model.events.tournament.TournamentService;
import at.jku.oeh.lan.laganizer.model.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private TournamentService tournamentService;

    public Team createTeam(long userId, long tournamentId) throws UserNotFoundException, TournamentNotFoundException {
        User user = userService.findUserById(userId);
        Tournament tournament = tournamentService.findTournamentById(tournamentId);
        Team team = new Team();
        team.setName(user.getName());
        team.addPlayer(user);
        team.setTournament(tournament);
        teamDAO.save(team);
        return team;
    }

    public Team renameTeam(long id, String name) throws TeamNotFoundException, InvalidTeamnameException {
        Team team = findTeamById(id);
        if (Validator.isValidTeamName(name)){
            team.setName(name);
            teamDAO.save(team);
        } else {
            throw new InvalidTeamnameException(name);
        }
        return team;
    }

    public Team findTeamById(long id) throws TeamNotFoundException {
        Team team = teamDAO.findOne(id);
        if (team == null) {
            throw new TeamNotFoundException();
        }
        return team;
    }

    public Team findTeamByName(String name) throws TeamNotFoundException {
        Team team = teamDAO.findTeamByName(name);
        if (team == null) {
            throw new TeamNotFoundException();
        }
        return team;
    }

    public Set<Team> findAllTeams() {
        Iterable<Team> teams = teamDAO.findAll();
        Set<Team> teamSet = new HashSet<>();
        for (Team team : teams) {
            teamSet.add(team);
        }
        return teamSet;

    }

    public Set<User> getPlayers(long id) throws TeamNotFoundException {
        Team team = findTeamById(id);
        // TODO perhaps load each player separately?
        return team.getPlayers();
    }

    public Team addPlayer(long teamId, long playerId) throws TeamNotFoundException, UserNotFoundException {
        Team team = findTeamById(teamId);
        User player = userService.findUserById(playerId);
        team.addPlayer(player);
        teamDAO.save(team);
        return team;
    }


    public Team removePlayer(long teamId, long playerId) throws TeamNotFoundException, UserNotFoundException {
        Team team = findTeamById(teamId);
        User player = userService.findUserById(playerId);
        team.removePlayer(player);
        teamDAO.save(team);
        return team;
    }

}
