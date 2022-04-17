package modele.web;

public interface SocketEvent {

	/**
	 * En cas d'erreur dans l'écoute, celle-ci sera récupérée ici.
	 * Par défaut, l'application relance une connexion après 10 secondes.
	 * @param e
	 */
	default void onError(Exception e) {

	}

	/**
	 * Retourne ici les messages envoyés par le serveur.
	 * Ne se déclenche pas en cas de message blanc.
	 * @param msg
	 */
	default void onReceive(String msg) {

	}
}
