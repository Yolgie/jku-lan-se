package at.jku.oeh.lan.laganizer.model.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserService {

    @Autowired
    private static UserDAO userDAO;
    /*TODO
    @Autowired
    private ClanDAO userDAO;
    */

    //FIXME Connect with OpenIDs/authorization
    public static User createUser(String name) throws InvalidUsernameException {
        isValidUsername(name);
        User user = new User();
        userDAO.save(user);
        return user;
    }

    //FIXME Connect with OpenIDs/authorization
    public static User setInactive(User user) throws UserNotFoundException {
        user = findUser(user);
        user.setActive(false);
        userDAO.save(user);
        return user;
    }


    public static User findById(long id) throws UserNotFoundException {
        User user = userDAO.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;

    }

    public static User findByName(String name) throws UserNotFoundException {
        User user = userDAO.findUserByName(name);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public static User setName(User user, String name) throws UserNotFoundException, InvalidUsernameException {
        user = findUser(user);
        if (isValidUsername(name)) {
            user.setName(name);
        }
        userDAO.save(user);

        return user;
    }

    public static User setEmail(User user, String email) throws UserNotFoundException, InvalidEmailException {
        user = findUser(user);
        if (isValidEmail(email)) {
            user.setEmail(email);
        }
        userDAO.save(user);
        return user;
    }

    public static User addRoles(User user, Set<String> roles) throws UserNotFoundException {
        user = findUser(user);
        Set<String> currentRoles = user.getRoles();
        currentRoles.addAll(roles);
        user.setRoles(roles);
        userDAO.save(user);
        return user;
    }

    public static User removeRoles(User user, Set<String> roles) throws UserNotFoundException {
        user = findUser(user);
        Set<String> currentRoles = user.getRoles();
        roles.forEach(currentRoles::remove);
        user.setRoles(currentRoles);
        userDAO.save(user);
        return user;
    }

    public static Set<User> findAll() {
        Iterable<User> users = userDAO.findAll();
        Set<User> userSet = new HashSet<>();
        for (User user : users) {
            userSet.add(user);
        }
        return userSet;
    }

    private static User findUser(User user) throws UserNotFoundException {
        return userDAO.findUserById(user.getId());
    }


    private static boolean isValidUsername(String name) throws InvalidUsernameException {
        //TODO implement constraints (RegEx?)
        if (name.length() < 3) {
            throw new InvalidUsernameException(name);
        }
        return true;
    }

    private static boolean isValidEmail(String email) throws InvalidEmailException {
        //TODO implement constraints (RegEx?)
        if (email.length() < 6 || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException(email);
        }
        return true;
    }
}
