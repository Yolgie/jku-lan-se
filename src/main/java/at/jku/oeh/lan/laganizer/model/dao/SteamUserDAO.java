package at.jku.oeh.lan.laganizer.model.dao;

import java.util.List;
import java.util.function.Consumer;

import at.jku.oeh.lan.laganizer.model.User;

public interface SteamUserDAO {
    List<User> findAllSteamUsers();
    void iterateOverSteamUsers(Consumer<List<User>> action);
}
