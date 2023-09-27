package controllers.forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;


/**
 * Example of validation of the form
 */

@Constraints.Validate
public class UserData implements Constraints.Validatable<List<ValidationError>> {

    protected String inputEmail;
;
    protected String password1;

    protected String password2;

    protected Integer age;

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "inputEmail='" + inputEmail + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public List<ValidationError> validate() {

        // keep all errors in this List
        final List<ValidationError> errors = new ArrayList<>();

        if (!inputEmail.contains("@")) {
            errors.add(new ValidationError("inputEmail", "Email must contain '@' character."));
        }

        if (inputEmail.isBlank()){
            errors.add(new ValidationError("inputEmail", "Email cannot be blank."));
        }

        if (password1.isBlank() || password2.isBlank() || password1.length()<=3 || password2.length()<=3) {
            errors.add(new ValidationError("password2", "Passwords must be longer than 3 characters and cannot be blank."));
        }

        if (!password1.equals(password2)) {
            errors.add(new ValidationError("password2", "Both passwords must be the same."));
        }

        // age must be between 1-99
        if (age==null || age < 1 || age > 99){
            errors.add(new ValidationError("age", "Age must be between 1-99."));
        }

        if (!errors.isEmpty()){
            // Also add a global error:
            errors.add(new ValidationError("", "Form could not be submitted."));
        }

        return (errors.isEmpty()) ? null : errors;

    }
}
