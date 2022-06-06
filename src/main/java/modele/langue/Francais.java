package modele.langue;

public class Francais implements ILangue {

	@Override
	public String getName() {
		return "Français";
	}

	@Override
	public String applicationDejaEnCoursExceptionMessage() {
		return "L'application est déjà en cours d'exécution.";
	}

	@Override
	public String applicationDejaEnCoursExceptionDescription() {
		return "Regardez dans vos icones windows pour y trouver l'application.";
	}

	@Override
	public String dataLoadingExceptionMessage() {
		return "Impossible de charger les données";
	}

	@Override
	public String dataLoadingExceptionDescription() {
		return "Veuillez supprimer le fichier de sauvegarde \"joueurs.txt\"";
	}

	@Override
	public String menuItemAjouter() {
		return "Ajouter";
	}

	@Override
	public String menuItemModifier() {
		return "Modifier";
	}

	@Override
	public String menuItemSupprimer() {
		return "Supprimer";
	}

	@Override
	public String menuItemRegarder() {
		return "Regarder";
	}

	@Override
	public String colonneNomLegende() {
		return "Nom";
	}

	@Override
	public String colonnePseudoLegende() {
		return "Pseudo";
	}

	@Override
	public String colonneIdLegende() {
		return "Id";
	}

	@Override
	public String colonneInGameLegende() {
		return "Connecté";
	}

	@Override
	public String colonneServeurLegende() {
		return "Serveur";
	}

	@Override
	public String commandeNonTrouveeExceptionMessage() {
		return "Impossible d'exécuter la commande voulue.";
	}

	@Override
	public String commandeNonTrouveeExceptionDescription() {
		return "La commande n'est pas dans le gestionnaire";
	}

	@Override
	public String playerNotFoundExceptionMessage() {
		return "Joueur non trouvé";
	}

	@Override
	public String playerNotFoundExceptionDescription() {
		return "Le joueur n'est pas dans la liste.";
	}

	@Override
	public String noLolInstallationFoundMessage() {
		return "L'installation n'a pu être trouvée.";
	}

	@Override
	public String noLolInstallationFoundDescription() {
		return "Veuillez vérifier que le dossier donné coorespond bien à celui de League of Legends.";
	}

	@Override
	public String regarderProcessExceptionMessage() {
		return "La partie n'a pu être lancée.";
	}

	@Override
	public String regarderProcessExceptionDescription() {
		return "Vérifiez que le dossier d'installation de league of Legends est correct et recommencez."
				+ "\nIl est aussi possible que l'erreur vienne de League of Legends.";
	}

	@Override
	public String joueurDejaPresentExceptionMessage() {
		return "Le joueur est déjà présent dans la table";
	}

	@Override
	public String joueurDejaPresentExceptionDescription() {
		return "Vous ne pouvez ajouter plusieurs fois le même joueur.";
	}

	@Override
	public String dataNotFoundExceptionMessage() {
		return "Le joueur n'existe pas";
	}

	@Override
	public String dataNotFoundExceptionDescription() {
		return "Aucun résultat pour le pseudo donné.";
	}

	@Override
	public String sauvegardeCorrompueExceptionMessage() {
		return "Impossible de charger la sauvegarde";
	}

	@Override
	public String sauvegardeCorrompueExceptionDescription() {
		return "La sauvegarde est vide ou corrompue, elle ne peut donc pas être chargée.";
	}

	@Override
	public String serviceCreationFailedExceptionMessage() {
		return "Une exception a eu lieu.";
	}

	@Override
	public String serviceCreationFailedExceptionDescription() {
		return "Veuillez redémarrer l'application";
	}

	@Override
	public String showAlertFromHeader() {
		return "Une erreur s'est produite.";
	}

	@Override
	public String showAlertFromContext() {
		return "Veuillez redémarrer l'application.";
	}

	@Override
	public String showWarningAlertJoueursDejaPresentsHeader() {
		return "Certains joueurs sont déjà présents dans la liste";
	}

	@Override
	public String showWarningAlertJoueursDejaPresentsListe() {
		return "Les joueurs suivants sont déjà présents";
	}

	@Override
	public String getInputStreamFromResourceFileNotFound() {
		return "Fichier non trouvé";
	}

	@Override
	public String trayIconServiceQuitter() {
		return "Quitter";
	}

	@Override
	public String trayIconServiceEnJeu() {
		return "est en jeu";
	}

}
