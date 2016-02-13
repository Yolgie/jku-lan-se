package jkulan.software.controllers;

/**
 * Created by tth on 1/18/16.
 */

import java.security.Principal;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jkulan.software.model.User;
import jkulan.software.model.UserDAO;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
public class UserController {
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------
	
	private static Log log = LogFactory.getLog(UserController.class);

    @Inject
    private UserDAO userDao;
	
    // ------------------------
    // PUBLIC METHODS
    // ------------------------
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}
	
    /**
     * /create  --> Create a new jkulan.software.model and save it in the database.
     *
     * @param address User's address
     * @param name User's name
     * @return A string describing if the jkulan.software.model is successfully created or not.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(String address, String name) {
        User user = null;
        try {
        	user = new User();
        	user.setAddress(address);
        	user.setName(name);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error creating the jkulan.software.model: " + ex.toString();
        }
        return "User succesfully created! (id = " + user.getId() + ")";
    }

    /**
     * /delete  --> Delete the jkulan.software.model having the passed id.
     *
     * @param id The id of the jkulan.software.model to delete
     * @return A string describing if the jkulan.software.model is succesfully deleted or not.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the jkulan.software.model:" + ex.toString();
        }
        return "User succesfully deleted!";
    }

    /**
     * /get-by-address  --> Return the id for the jkulan.software.model having the passed address.
     *
     * @param address The address to search in the database.
     * @return The jkulan.software.model id or a message error if the jkulan.software.model is not found.
     */
    @RequestMapping("/get-by-address")
    @ResponseBody
    public String getByAddress(String address) {
        String userId;
        try {
            User user = userDao.findByAddress(address);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
        	log.error("User not found", ex);
            return "User not found";
        }
        return "The jkulan.software.model id is: " + userId;
    }

    /**
     * /update  --> Update the address and the name for the jkulan.software.model in the database
     * having the passed id.
     *
     * @param id The id for the jkulan.software.model to update.
     * @param address The new address.
     * @param name The new name.
     * @return A string describing if the jkulan.software.model is succesfully updated or not.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(long id, String address, String name) {
        try {
            User user = userDao.findOne(id);
            user.setAddress(address);
            user.setName(name);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error updating the jkulan.software.model: " + ex.toString();
        }
        return "User succesfully updated!";
    }
}
