package at.jku.oeh.lan.laganizer.dto;

import at.jku.oeh.lan.laganizer.model.User;

/**
 * Used to transfer information, needed in FrontEnd.
 */
public class UserDTO {

    private long id;
    private String name;
    private String email;

    public UserDTO(){

    }

    public UserDTO(User u) {
        this.id = u.getId();
        this.name = u.getName();
        this.email = u.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
