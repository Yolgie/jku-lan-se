package at.jku.oeh.lan.laganizer.model.events.tournament;


import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.base.UserService;
import at.jku.oeh.lan.laganizer.model.events.EventService;
import at.jku.oeh.lan.laganizer.model.events.TimeService;
import at.jku.oeh.lan.laganizer.model.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TournamentService extends EventService {
    @Autowired
    private at.jku.oeh.lan.laganizer.model.dao.TournamentDAO tournamentDAO;
    @Autowired
    private TimeService timeService;
    @Autowired
    private UserService userService;

    public Tournament createTournament(String game, int teamSize, long eventManagerUserId, String startTime, String description, String name) throws UserNotFoundException {
        Tournament tnmt = new Tournament();

        tnmt.setGame(game);
        tnmt.setMaxTeamSize(teamSize);
        // event
        tnmt.setName(name);
        tnmt.setDescription(description);
        tnmt.setEventManager(userService.findUserById(eventManagerUserId));
        tnmt.setStartTime(timeService.StringToInstant(startTime));
        tnmt.setEnabled(false);

        tournamentDAO.save(tnmt);
        return tnmt;
    }

    public Tournament findTournamentById(long id) throws TournamentNotFoundException {
        Tournament tmnt = tournamentDAO.findOne(id);
        if (tmnt == null) {
            throw new TournamentNotFoundException();
        }
        return tmnt;
    }


    public Tournament findTournamentByGame(String game) throws TournamentNotFoundException {
        Tournament tmnt = tournamentDAO.findByGame(game);
        if (tmnt == null) {
            throw new TournamentNotFoundException();
        }
        return tmnt;
    }


    public Set<Tournament> findAllTournaments() {
        return Converter.iterableToSet(tournamentDAO.findAll());
    }
}
