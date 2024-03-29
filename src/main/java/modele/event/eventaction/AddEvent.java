package modele.event.eventaction;

import javafx.concurrent.WorkerStateEvent;
import modele.affichage.ViewElement;
import modele.commande.CommandeAjout;
import modele.exception.JoueurDejaPresentException;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import modele.tache.TacheCharger;
import service.GestionnaireCommandeService;
import service.ServiceManager;
import service.WebRequestScheduler;

public class AddEvent extends RunnableEventWithTable<JoueurFx> {

	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);

	private final String nom;
	private final String pseudo;
	private final Serveur serveur;

	public AddEvent(final ViewElement<JoueurFx> table, final String nom, final String pseudo, final Serveur serveur) {
		super(table);
		this.nom = nom;
		this.pseudo = pseudo;
		this.serveur = serveur;
	}

	@Override
	public Void execute() {
		if(pseudo == null || pseudo.isBlank()) {
			return null;
		}
		final var tache = new TacheCharger(nom, pseudo, serveur);
		tache.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, t -> {
			final var joueur = tache.getValue();
			if(table.getItems().stream().anyMatch(items -> joueur.getPlayerId().equals(items.getPlayerId()))) {
				alerteService.alert(new JoueurDejaPresentException());
				return;
			}
			gestionnaireCommandeService.addCommande(new CommandeAjout(table, joueur)).executer();
		});
		tache.run();

		return null;
	}
}