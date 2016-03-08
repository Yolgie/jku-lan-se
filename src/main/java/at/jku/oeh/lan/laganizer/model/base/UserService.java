package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClanService clanService;

    //FIXME Connect with OpenIDs/authorization
    public User createUser(String name) throws InvalidUsernameException {
        if (!Validator.isValidUserName(name)) {
            throw new InvalidUsernameException(name);
        }
        User user = new User();
        user.setName(name);
        user.setActive(true);
        user.setClan(clanService.createClan(user));
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
            if (user.isActive()) {
                userSet.add(user);
            }
        }
        return userSet;
    }

    public Set<User> findInactiveUsers() {
        Iterable<User> users = userDAO.findAll();
        Set<User> userSet = new HashSet<>();
        for (User user : users) {
            if (!user.isActive()) {
                userSet.add(user);
            }
        }
        return userSet;
    }

    public User setUserName(long id, String name) throws UserNotFoundException, InvalidUsernameException {
        User user = findUserById(id);
        if (Validator.isValidUserName(name)) {
            user.setName(name);
        } else {
            throw new InvalidUsernameException(name);
        }
        userDAO.save(user);

        return user;
    }

    public User setUserEmail(long id, String email) throws UserNotFoundException, InvalidEmailException {
        User user = findUserById(id);
        if (Validator.isValidEmail(email)) {
            user.setEmail(email);
            userDAO.save(user);
        } else {
            throw new InvalidEmailException(email);
        }
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
        User user = findUserById(id);
        user.setActive(false);
        clanService.removeUser(user.getClan(), user);
        user.setClan(null);
        userDAO.save(user);
        return user;
    }

    public User addUserToClan(long userId, long clanId) throws UserNotFoundException, ClanNotFoundException {
        User user = findUserById(userId);
        Clan clan = clanService.findClanById(clanId);

        clanService.removeUser(user.getClan(), user);
        clanService.addUser(clan, user);
        user.setClan(clan);

        userDAO.save(user);
        return user;
    }

    public User removeUserFromClan(long userId, long clanId) throws UserNotFoundException, ClanNotFoundException {
        User user = findUserById(userId);
        Clan clan = clanService.findClanById(clanId);

        clanService.removeUser(clan, user);
        clanService.createClan(user);
        user.setClan(clan);

        userDAO.save(user);
        return user;
    }

}
