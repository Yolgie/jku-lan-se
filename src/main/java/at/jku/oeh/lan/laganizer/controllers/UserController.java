package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.io.Serializable;

@RestController
@PermitAll
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserDAO userDao;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public RESTDataWrapperDTO<User> search(@RequestParam String name) {
        return new RESTDataWrapperDTO<User>(userDao.findUserByName(name), true);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTDataWrapperDTO<User> show(@PathVariable long id) {
        return new RESTDataWrapperDTO<>(userDao.findOne(id), true);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public RESTDataWrapperDTO<User> update(@PathVariable long id, @RequestParam String name) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        User user = userDao.findOne(id);
        user.setName(name);
        userDao.save(user);

        result.setData(user);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<User> create(@RequestParam String name) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        User user = new User();
        user.setName(name);
        userDao.save(user);

        result.setData(user);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public RESTDataWrapperDTO<User> delete(@PathVariable long id) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        if (userDao.exists(id)) {
            try {
                userDao.delete(id);
                result.setSuccess(true);
            } catch (IllegalArgumentException e) {
                result.setSuccess(false);
                result.setErrorDetails(e.getMessage());
            }
        } else {
            result.setSuccess(false);
            result.setErrorDetails("User does not exist");
        }
        return result;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO<>((Serializable) userDao.findAll(), true);
    }

}
