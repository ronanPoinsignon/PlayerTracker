package service;

import java.util.ArrayList;

import modele.commande.Commande;
import modele.commande.CommandeInterface;
import modele.commande.exception.CommandeNonTrouveeException;

/**
 * Classe permettant de gérer les différentes commandes à effectuer.
 * @author ronan
 *
 */
public class GestionnaireCommandeService implements IService {

	private final ArrayList<CommandeInterface> listeCommandes = new ArrayList<>();
	private final ArrayList<CommandeInterface> listeCommandesEffectuees = new ArrayList<>();
	private final ArrayList<CommandeInterface> listeCommandesAnnulees = new ArrayList<>();
	private final ArrayList<CommandeInterface> listeCommandesAReexecuteer = new ArrayList<>();

	/**
	 * Ajoute une commande à la liste des commandes devant être effectuées.
	 * @param commande
	 */
	public GestionnaireCommandeService addCommande(final Commande<?> commande) {
		listeCommandes.add(commande);
		return this;
	}

	/**
	 * Exécute la prochaine commande de la liste des commandes à faire.
	 */
	public GestionnaireCommandeService executer() {
		final var commande = listeCommandes.remove(0);
		if(commande.executer()) {
			listeCommandesEffectuees.add(commande);
			listeCommandesAReexecuteer.clear();
		}
		return this;
	}

	/**
	 * Exécute toute les commandes de la liste des commandes à faire.
	 */
	public GestionnaireCommandeService executerAll() {
		for(final CommandeInterface commande : listeCommandes) {
			if(commande.executer()) {
				listeCommandesEffectuees.add(commande);
				listeCommandes.remove(commande);
			}
		}
		listeCommandesAReexecuteer.removeAll(listeCommandes);
		return this;
	}

	/**
	 * Exécute une commande de la liste des commandes à faire. Renvoie une exception
	 * si la commande ne se trouve pas dans cette liste.
	 * @param commande
	 * @throws CommandeNonTrouveeException
	 */
	public GestionnaireCommandeService executer(final Commande<?> commande) throws CommandeNonTrouveeException {
		if(!listeCommandes.contains(commande)) {
			throw new CommandeNonTrouveeException();
		}
		if(commande.executer()) {
			commande.executer();
			listeCommandes.remove(commande);
			listeCommandesEffectuees.add(commande);
			listeCommandesAReexecuteer.removeAll(listeCommandes);
		}
		return this;
	}

	public GestionnaireCommandeService executerInstant(final Commande<?> commande) {
		commande.executer();
		return this;
	}

	/**
	 * Annule la dernière commande effectuée.
	 */
	public GestionnaireCommandeService annuler() {
		final var commande = listeCommandesEffectuees.remove(listeCommandesEffectuees.size() - 1);
		if(commande.annuler()) {
			listeCommandesAnnulees.add(commande);
			listeCommandesAReexecuteer.add(commande);
		}
		return this;
	}

	/**
	 * Réexécute la commande annulée.
	 */
	public GestionnaireCommandeService reexecuter() {
		final var commande = listeCommandesAReexecuteer.remove(listeCommandesAReexecuteer.size() - 1);
		if(commande.reexecuter()) {
			listeCommandesEffectuees.add(commande);
		}
		return this;
	}

	public GestionnaireCommandeService clean() {
		listeCommandes.clear();
		listeCommandesAnnulees.clear();
		listeCommandesAReexecuteer.clear();
		listeCommandesEffectuees.clear();
		return this;
	}
	/**
	 * Retourne TRUE si il est possible d'annuler la dernière commande effectuée.
	 * @return
	 */
	public boolean canAnnuler() {
		return !listeCommandesEffectuees.isEmpty();
	}

	/**
	 * Retourne TRUE si il est possible de refaire la dernière commande annulée.
	 * @return
	 */
	public boolean canReexecuter() {
		return !listeCommandesAReexecuteer.isEmpty();
	}
}