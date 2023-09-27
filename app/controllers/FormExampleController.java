package controllers;

import controllers.forms.UserData;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

// tutorial how to use forms

public class FormExampleController extends Controller {

    final org.slf4j.Logger logger = LoggerFactory.getLogger(FormExampleController.class);

    @Inject
    FormFactory formFactory;

    /**
     * Show empty form
     */
    @AddCSRFToken
    public Result showForm(Http.Request request) {

        Optional<CSRF.Token> CSRFtoken = CSRF.getToken(request);

        Form<UserData> userForm = formFactory.form(UserData.class);

        UserData userData = new UserData();
        userForm = userForm.fill(userData);

        return ok(views.html.formExample.render(userForm, CSRFtoken.get().value()));
    }

    /**
     * Show form with example data, those could be for example from the database
     */
    @AddCSRFToken
    public Result showFormWithData(Http.Request request) {

        Optional<CSRF.Token> token = CSRF.getToken(request);

        // this data can be read from the database, if you are doing editing of existing data instead of new date
        UserData userData = new UserData();
        userData.setInputEmail("some@email.com");
        userData.setPassword1("123456");
        userData.setPassword2("123456");
        userData.setAge(77);

        // fill the form object with the existing data
        Form<UserData> userForm = formFactory.form(UserData.class);
        userForm = userForm.fill(userData);

        return ok(views.html.formExample.render(userForm, token.get().value()));
    }


    /**
     * Submit the form, show errors if needed or ok message
     */
    @AddCSRFToken
    @RequireCSRFCheck
    public Result submitForm(Http.Request request) {

        Optional<CSRF.Token> token = CSRF.getToken(request);

        // load data from request
        // it will be validated using validation method in UserData.java
        Form<UserData> userForm = formFactory.form(UserData.class).bindFromRequest(request);

        // if there are errors
        if (userForm.hasErrors()) {
            return ok(views.html.formExample.render(userForm, token.get().value()));
        } else {
        // no errors
            UserData userData = userForm.get();
            return ok("Submit ok\nGot user email: " + userData.toString());
        }

    }



}
