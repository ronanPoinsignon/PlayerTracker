package modele.langue;

public interface ILangue {

	String getName();

	// ApplicationDejaEnCoursException
	String applicationDejaEnCoursExceptionMessage();
	String applicationDejaEnCoursExceptionDescription();

	// DataLoadingException
	String dataLoadingExceptionMessage();
	String dataLoadingExceptionDescription();

	// ControllerPagePrincipale
	String menuItemAjouter();
	String menuItemModifier();
	String menuItemSupprimer();
	String menuItemRegarder();
	String colonneNomLegende();
	String colonnePseudoLegende();
	String colonneIdLegende();
	String colonneInGameLegende();
	String colonneServeurLegende();

	// CommandeNonTrouveeException
	String commandeNonTrouveeExceptionMessage();
	String commandeNonTrouveeExceptionDescription();


	// PlayerNotFoundException
	String playerNotFoundExceptionMessage();
	String playerNotFoundExceptionDescription();

	// NoLolInstallationFound
	String noLolInstallationFoundMessage();
	String noLolInstallationFoundDescription();

	// RegarderProcessException
	String regarderProcessExceptionMessage();
	String regarderProcessExceptionDescription();

	// JoueurDejaPresentException
	String joueurDejaPresentExceptionMessage();
	String joueurDejaPresentExceptionDescription();

	// DataNotFoundException
	String dataNotFoundExceptionMessage();
	String dataNotFoundExceptionDescription();

	// SauvegardeCorrompueException
	String sauvegardeCorrompueExceptionMessage();
	String sauvegardeCorrompueExceptionDescription();

	// ServiceCreationFailedException
	String serviceCreationFailedExceptionMessage();
	String serviceCreationFailedExceptionDescription();

	// AlertFxService
	String showAlertFromHeader();
	String showAlertFromContext();

	// showWarningAlertJoueursDejaPresents
	String showWarningAlertJoueursDejaPresentsHeader();
	String showWarningAlertJoueursDejaPresentsListe();

	// FileManager
	String getInputStreamFromResourceFileNotFound();

	// TrayIconService
	String trayIconServiceQuitter();
	String trayIconServiceEnJeu();
}
