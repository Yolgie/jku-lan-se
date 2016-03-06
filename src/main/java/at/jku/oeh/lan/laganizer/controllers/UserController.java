package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public RESTDataWrapperDTO<User> search(@RequestParam String name) {
        RESTDataWrapperDTO<User> result;
        try {
            result = new RESTDataWrapperDTO<>(userService.findUserByName(name)
                    , true);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
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
            result = new RESTDataWrapperDTO<>(userService.findUserById(id)
                    , true);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            result = new RESTDataWrapperDTO<>();
            result.setSuccess(false);
            result.setErrorDetails("Username not found");
        }
        return result;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTDataWrapperDTO<User> update(@PathVariable long id, @RequestParam String name) {
        RESTDataWrapperDTO<User> result = new RESTDataWrapperDTO<>();
        try {
            result.setData(userService.setUserName(id, name));
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
            User user = userService.createUser(name);
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
            result.setData(userService.deleteUserById(id));
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
        return new RESTDataWrapperDTO((Serializable) userService.findAllUsers(), true);
    }
}
