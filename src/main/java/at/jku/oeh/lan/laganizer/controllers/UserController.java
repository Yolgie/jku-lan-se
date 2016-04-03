package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.base.InvalidUsernameException;
import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

import java.io.Serializable;

import javax.annotation.security.PermitAll;

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

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public void updateByJSON(@RequestBody UserDTO userJson) {
        User user = userDao.findById(userJson.getId());
        user.setName(userJson.getName());
        user.setEmail(userJson.getEmail());
        userDao.save(user);
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

    @RequestMapping(value= "current", method = RequestMethod.GET)
    public @ResponseBody UserDTO currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof  ClientAuthenticationToken) {
            ClientAuthenticationToken token = (ClientAuthenticationToken) auth;
            User u = (User) token.getUserDetails();
            return new UserDTO(u);
        }
        return null;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody List<UserDTO> list() {
        Iterable<User> users = userDao.findAll();
        List<UserDTO> userDtos = new ArrayList<>();
        for(User u: users) {
            userDtos.add(new UserDTO(u));
        }
        return userDtos;
    }

}
