package controllers.forms;

// DTO class to hold login information for loginAPI call

import play.data.validation.Constraints;

public class UserLogin  {

    @Constraints.Required
    @Constraints.Email
    protected String username;

    @Constraints.Required
    protected String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
