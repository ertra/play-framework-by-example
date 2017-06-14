package services;

import akka.actor.ActorSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OnStartupService {

    @Inject
    private OnStartupService() {
      System.out.println(" ----------- App is starting --------------");
    }

}
