package modele.event.eventaction;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.TableView;
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

	public AddEvent(final TableView<JoueurFx> table, final String nom, final String pseudo, final Serveur serveur) {
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
		if(table.getItems().stream().anyMatch(joueur -> pseudo.equals(joueur.getPseudo()))) {
			throw new JoueurDejaPresentException(pseudo);
		}
		final var tache = new TacheCharger(nom, pseudo, serveur);
		tache.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, t -> {
			final var joueur = tache.getValue();
			gestionnaireCommandeService.addCommande(new CommandeAjout(table, joueur)).executer();
		});
		final var t = new Thread(tache);
		t.setDaemon(true);
		t.start();
		return null;
	}
}