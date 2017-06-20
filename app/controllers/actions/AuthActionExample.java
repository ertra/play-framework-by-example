package controllers.actions;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;

import java.util.concurrent.CompletionStage;

public class AuthActionExample extends play.mvc.Action.Simple {

    public CompletionStage<Result> call(Http.Context ctx) {
        Logger.info("Calling action for {}", ctx);
        System.out.println("AuthActionExample was triggered");

        // to send an object to the Controller
        ctx.args.put("user", "OK");
        return delegate.call(ctx);
    }

}
