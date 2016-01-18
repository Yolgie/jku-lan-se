package jkulan.software.controllers;

/**
 * Created by tth on 1/18/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jkulan.software.model.User;
import jkulan.software.model.UserDAO;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
public class UserController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * /create  --> Create a new jkulan.software.model and save it in the database.
     *
     * @param address User's address
     * @param name User's name
     * @return A string describing if the jkulan.software.model is succesfully created or not.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(String address, String name) {
        User user = null;
        try {
            user = new User(address, name);
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

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private UserDAO userDao;

}
