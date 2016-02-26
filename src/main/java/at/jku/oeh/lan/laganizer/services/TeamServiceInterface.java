package at.jku.oeh.lan.laganizer.services;

import at.jku.oeh.lan.laganizer.model.Team;
import at.jku.oeh.lan.laganizer.model.Tournament;
import at.jku.oeh.lan.laganizer.model.User;

import java.util.List;

public interface TeamServiceInterface {
    public Team createTeam(Tournament tournament, User creator);

    public void deleteTeam(Team team);

    public void setCreatorOfTeam(Team team, User user);

    public void renameTeam(Team team, String name);

    public void addPlayerToTeam(Team team, User user);

    public void removePlayerFromTeam(Team team, User user);

    public List<Team> findTeamsByTournament(Tournament tournament);

    public List<Team> findTeamsByUser(User user);

    public List<Team> listTeams();
}
