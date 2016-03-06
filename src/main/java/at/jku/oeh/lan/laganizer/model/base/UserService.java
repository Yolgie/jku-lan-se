package at.jku.oeh.lan.laganizer.model.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    /*TODO
    @Autowired
    private ClanDAO userDAO;
    */

    //FIXME Connect with OpenIDs/authorization
    public User createUser(String name) throws InvalidUsernameException {
        isValidUsername(name);
        User user = new User();
        user.setName(name);
        user.setActive(true);
        userDAO.save(user);
        return user;
    }

    public User findUserById(long id) throws UserNotFoundException {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " can't be found");
        }
        return user;

    }

    public User findUserByName(String name) throws UserNotFoundException {
        User user = userDAO.findByName(name);
        if (user == null) {
            throw new UserNotFoundException("Username " + name + " can't be found");
        }
        return user;
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("E-Mail " + email + " can't be found");
        }
        return user;
    }

    public Set<User> findAllUsers() {
        Iterable<User> users = userDAO.findAll();
        Set<User> userSet = new HashSet<>();
        for (User user : users) {
            userSet.add(user);
        }
        return userSet;
    }

    public User setUserName(long id, String name) throws UserNotFoundException, InvalidUsernameException {
        User user = findUserById(id);
        if (isValidUsername(name)) {
            user.setName(name);
        }
        userDAO.save(user);

        return user;
    }

    public User setUserEmail(long id, String email) throws UserNotFoundException, InvalidEmailException {
        User user = findUserById(id);
        if (isValidEmail(email)) {
            user.setEmail(email);
        }
        userDAO.save(user);
        return user;
    }

    public User addUserRoles(long id, Set<String> roles) throws UserNotFoundException {
        User user = findUserById(id);
        Set<String> currentRoles = user.getRoles();
        currentRoles.addAll(roles);
        user.setRoles(currentRoles);
        userDAO.save(user);
        return user;
    }

    public User removeUserRoles(long id, Set<String> roles) throws UserNotFoundException {
        User user = findUserById(id);
        Set<String> currentRoles = user.getRoles();
        roles.forEach(currentRoles::remove);
        user.setRoles(currentRoles);
        userDAO.save(user);
        return user;
    }

    // Actually, 'hide' user instead of finally deleting it...
    public User deleteUserById(long id) throws UserNotFoundException {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + "can't be deleted - ID not found");
        }
        user.setActive(false);
        userDAO.save(user);
        return user;
    }


    private boolean isValidUsername(String name) throws InvalidUsernameException {
        //TODO implement constraints (RegEx?)
        if (name.length() < 3) {
            throw new InvalidUsernameException(name);
        }
        return true;
    }

    private boolean isValidEmail(String email) throws InvalidEmailException {
        //TODO implement constraints (RegEx?)
        if (email.length() < 6 || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException(email);
        }
        return true;
    }
}
