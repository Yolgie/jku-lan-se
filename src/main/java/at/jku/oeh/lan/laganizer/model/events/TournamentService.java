package at.jku.oeh.lan.laganizer.model.events;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {
    @Autowired
    private TournamentDAO tournamentDAO;

    public Tournament findById(long tournamentId) {
        return null;
    }
}
