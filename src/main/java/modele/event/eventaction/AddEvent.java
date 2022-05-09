package modele.event.eventaction;

import javafx.concurrent.WorkerStateEvent;
import modele.affichage.ViewElement;
import modele.commande.CommandeAjout;
import modele.event.eventaction.exception.JoueurDejaPresentException;
import modele.joueur.JoueurFx;
import modele.tache.TacheCharger;
import service.GestionnaireCommandeService;
import service.ServiceManager;
import service.WebRequestScheduler;

public class AddEvent extends RunnableEvent<JoueurFx> {

	private GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);

	private String nom;
	private String pseudo;

	public AddEvent(ViewElement<JoueurFx> table, String nom, String pseudo) {
		super(table);
		this.nom = nom;
		this.pseudo = pseudo;
	}

	@Override
	public Void execute() {
		if(pseudo == null || pseudo.isBlank()) {
			return null;
		}
		if(table.getItems().stream().anyMatch(joueur -> pseudo.equals(joueur.getPseudo()))) {
			throw new JoueurDejaPresentException(nom);
		}
		var tache = new TacheCharger(nom, pseudo);
		tache.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, t -> {
			JoueurFx joueur = tache.getValue();
			gestionnaireCommandeService.addCommande(new CommandeAjout(table, joueur)).executer();
		});
		var t = new Thread(tache);
		t.setDaemon(true);
		t.start();
		return null;
	}
}

