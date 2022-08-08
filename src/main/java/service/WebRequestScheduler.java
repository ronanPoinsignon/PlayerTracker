package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import modele.joueur.Joueur;
import modele.webrequestrunnable.WebRequestOnePlayerRunnable;
import modele.webrequestrunnable.WebRequestRunnable;
import modele.webrequestrunnable.builder.WebRequestBuilder;

public class WebRequestScheduler implements IService {

	private final ScheduledExecutorService scheduler;
	private final WebRequestRunnable runnable;

	public WebRequestScheduler() {
		scheduler = Executors.newScheduledThreadPool(1);
		runnable = WebRequestBuilder.withPlayerList().build();
		scheduler.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.MINUTES);
	}

	public void addJoueur(final Joueur joueur) {
		runnable.add(joueur);
	}

	public void removeJoueur(final Joueur joueur) {
		runnable.remove(joueur);
	}

	public void stopScheduler() {
		scheduler.shutdownNow();
	}

	public void executeNow() {
		scheduler.execute(runnable);
	}

	public void executeNow(final Joueur joueur) {
		scheduler.execute(new WebRequestOnePlayerRunnable(joueur));
	}
}