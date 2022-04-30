package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import modele.joueur.Joueur;
import modele.scheduler.WebRequestRunnable;

public class WebRequestScheduler implements IService {

	private ScheduledExecutorService scheduler;
	private WebRequestRunnable runnable;

	public WebRequestScheduler() {
		scheduler = Executors.newScheduledThreadPool(1);
		runnable = new WebRequestRunnable();
		scheduler.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.MINUTES);
	}

	public void addJoueur(Joueur joueur) {
		runnable.add(joueur);
	}

	public void removeJoueur(Joueur joueur) {
		runnable.remove(joueur);
	}

	public void stopScheduler() {
		scheduler.shutdownNow();
	}

	public void executeNow() {
		scheduler.execute(runnable);
	}
}
