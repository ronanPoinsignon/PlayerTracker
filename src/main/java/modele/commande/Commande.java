package modele.commande;

/**
 * Classe abstraite permettant d'effectuer des actions sur la liste de joueurs.
 * @author ronan
 *
 */
public abstract class Commande<T> implements CommandeInterface {

	protected CommandeUtil<T> commandeUtil = new CommandeUtil<>();

}