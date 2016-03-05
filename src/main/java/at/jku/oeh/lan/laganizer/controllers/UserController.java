package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserDAO userDao;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public RESTDataWrapperDTO<User> search(@RequestParam String name) {
        RESTDataWrapperDTO<User> result;
        try {
            result = new RESTDataWrapperDTO<>(UserService.findByName(name)
                    , true);
        } catch (UserNotFoundException e) {
            e.printStackTrace(); //FIXME
            result = new RESTDataWrapperDTO<>();
            result.setSuccess(false);
            result.setErrorDetails("Username not found");
        }
        return result;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTDataWrapperDTO<User> show(@PathVariable long id) {
        RESTDataWrapperDTO<User> result;
        try {
            result = new RESTDataWrapperDTO<>(UserService.findById(id)
                    , true);
        } catch (UserNotFoundException e) {
            e.printStackTrace(); //FIXME
            result = new RESTDataWrapperDTO<>();
            result.setSuccess(false);
            result.setErrorDetails("Username not found");
        }
        return result;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public RESTDataWrapperDTO<User> update(@PathVariable long id, @RequestParam String name) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        try {
            User user = UserService.findById(id);
            result.setData(UserService.setName(user, name));
            result.setSuccess(true);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorDetails("UserID not found");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorDetails("Username invalid");
        }
        return result;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<User> create(@RequestParam String name) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        try {
            User user = UserService.createUser(name);
            result.setData(user);
            result.setSuccess(true);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorDetails("Username invalid");
        }
        return result;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public RESTDataWrapperDTO<User> delete(@PathVariable long id) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        try {
            User user = UserService.findById(id);
            result.setData(UserService.setInactive(user));
            result.setSuccess(true);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result.setErrorDetails("UserID not found");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO((Serializable) UserService.findAll(), true);
    }
}
