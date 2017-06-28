package services;

import akka.actor.ActorSystem;
import play.Logger;
import play.Play;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class OnStartupService {

    @Inject
    public OnStartupService(ApplicationLifecycle appLifecycle1) {

        // this is executed only once when the app starts
        System.out.println(" ----------- App is starting --------------");

    }

}
