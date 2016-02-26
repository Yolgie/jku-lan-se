package at.jku.oeh.lan.laganizer.services;

import at.jku.oeh.lan.laganizer.model.User;

import java.util.List;

public interface UserServiceInterface {
    public User createUser();

    public User renameUser(String name);

    public List<User> findUser(String name);

    public void deleteUser(User user);

    public List<User> listUsers();
}
