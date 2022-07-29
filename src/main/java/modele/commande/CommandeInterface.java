package modele.commande;

public interface CommandeInterface {

	boolean executer();
	boolean annuler();

	default boolean reexecuter() {
		return executer();
	}
}