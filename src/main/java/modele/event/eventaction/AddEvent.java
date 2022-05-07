package modele.event.eventaction;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.TableView;
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

	public AddEvent(TableView<JoueurFx> table, String nom, String pseudo) {
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
		//		labelIndicateur.textProperty().unbind();
		//		labelIndicateur.textProperty().bind(tache.messageProperty());
		//		indicateur.progressProperty().unbind();
		//		indicateur.progressProperty().bind(tache.progressProperty());
		tache.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, t -> {
			JoueurFx joueur = tache.getValue();
			//					labelIndicateur.textProperty().unbind();
			//					labelIndicateur.setText(joueur.getAppellation() + " trouvé");
			gestionnaireCommandeService.addCommande(new CommandeAjout(table, joueur)).executer();
			//					if(!tache.getListeUrlsMauvaisLien().isEmpty() || !tache.getListeUrlsErreur().isEmpty()) {
			//						logger.showErrorAlertVideosNonChargees(tache.getListeUrlsMauvaisLien(), tache.getListeUrlsErreur());
			//					}
			//					updateActionPossibleGestionnaire();
		});
		var t = new Thread(tache);
		t.setDaemon(true);
		t.start();
		return null;
	}
}

