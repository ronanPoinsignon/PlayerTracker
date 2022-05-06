package modele.commande;

/**
 * Classe abstraite permettant d'effectuer des actions sur la liste de vidéos.
 * @author ronan
 *
 */
public abstract class Commande implements CommandeInterface {

	protected CommandeUtil commandeUtil = new CommandeUtil();

}
