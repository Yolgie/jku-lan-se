package at.jku.oeh.lan.laganizer.model.dao;

import java.util.List;

import at.jku.oeh.lan.laganizer.model.User;

public interface SteamUserDAO {
    List<User> findAllSteamUsers();
    void iterateOverSteamUsers(TransactionalConsumer<List<User>> action);
}
