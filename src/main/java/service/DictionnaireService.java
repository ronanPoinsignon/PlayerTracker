package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modele.properties.CaselessProperties;

public class DictionnaireService implements IService {

	FileManager fm;
	PropertiesService ps;

	private File langue;

	// ApplicationDejaEnCoursException
	private final StringProperty applicationDejaEnCoursExceptionMessage = new SimpleStringProperty();
	private final StringProperty applicationDejaEnCoursExceptionDescription = new SimpleStringProperty();

	// DataLoadingException
	private final StringProperty dataLoadingExceptionMessage = new SimpleStringProperty();
	private final StringProperty dataLoadingExceptionDescription = new SimpleStringProperty();

	// BadOsExceptionMessage
	private final StringProperty badOsExceptionMessage = new SimpleStringProperty();
	private final StringProperty badOsExceptionDescription = new SimpleStringProperty();

	// ControllerPagePrincipale
	private final StringProperty menuItemAjouter = new SimpleStringProperty();
	private final StringProperty menuItemModifier = new SimpleStringProperty();
	private final StringProperty menuItemSupprimer = new SimpleStringProperty();
	private final StringProperty menuItemRegarder = new SimpleStringProperty();
	private final StringProperty colonneNomLegende = new SimpleStringProperty();
	private final StringProperty colonnePseudoLegende = new SimpleStringProperty();
	private final StringProperty colonneIdLegende = new SimpleStringProperty();
	private final StringProperty colonneInGameLegende = new SimpleStringProperty();
	private final StringProperty colonneServeurLegende = new SimpleStringProperty();
	private final StringProperty nomPlaceHolder = new SimpleStringProperty();
	private final StringProperty pseudoPlaceHolder = new SimpleStringProperty();

	// CommandeNonTrouveeException
	private final StringProperty commandeNonTrouveeExceptionMessage = new SimpleStringProperty();
	private final StringProperty commandeNonTrouveeExceptionDescription = new SimpleStringProperty();


	// PlayerNotFoundException
	private final StringProperty playerNotFoundExceptionMessage = new SimpleStringProperty();
	private final StringProperty playerNotFoundExceptionDescription = new SimpleStringProperty();

	// NoLolInstallationFound
	private final StringProperty noLolInstallationFoundMessage = new SimpleStringProperty();
	private final StringProperty noLolInstallationFoundDescription = new SimpleStringProperty();

	// RegarderProcessException
	private final StringProperty regarderProcessExceptionMessage = new SimpleStringProperty();
	private final StringProperty regarderProcessExceptionDescription = new SimpleStringProperty();

	// JoueurDejaPresentException
	private final StringProperty joueurDejaPresentExceptionMessage = new SimpleStringProperty();
	private final StringProperty joueurDejaPresentExceptionDescription = new SimpleStringProperty();

	// DataNotFoundException
	private final StringProperty dataNotFoundExceptionMessage = new SimpleStringProperty();
	private final StringProperty dataNotFoundExceptionDescription = new SimpleStringProperty();

	// SauvegardeCorrompueException
	private final StringProperty sauvegardeCorrompueExceptionMessage = new SimpleStringProperty();
	private final StringProperty sauvegardeCorrompueExceptionDescription = new SimpleStringProperty();

	// ServiceCreationFailedException
	private final StringProperty serviceCreationFailedExceptionMessage = new SimpleStringProperty();
	private final StringProperty serviceCreationFailedExceptionDescription = new SimpleStringProperty();

	// AlertFxService
	private final StringProperty showAlertFromHeader = new SimpleStringProperty();
	private final StringProperty showAlertFromContext = new SimpleStringProperty();

	// showWarningAlertJoueursDejaPresents
	private final StringProperty showWarningAlertJoueursDejaPresentsHeader = new SimpleStringProperty();
	private final StringProperty showWarningAlertJoueursDejaPresentsListe = new SimpleStringProperty();

	// FileManager
	private final StringProperty getInputStreamFromResourceFileNotFound = new SimpleStringProperty();

	// TrayIconService
	private final StringProperty trayIconServiceQuitter = new SimpleStringProperty();
	private final StringProperty trayIconServiceEnJeu = new SimpleStringProperty();

	public DictionnaireService() {

	}

	/**
	 * Méthode permettant de mettre à jour tous les champs de l'application.
	 * Utilise la récursivité pour une gestion moins encombrante des attributs.
	 * Cependant, cette gestion impose d'avoir exactement le même nom entre l'attribut et la méthode côté {@link ILangue}.
	 * @param langue
	 */
	public void setLangue(final File langue) {
		this.langue = langue;
		final var fields = this.getClass().getDeclaredFields();
		final var properties = new CaselessProperties();
		try(var is = new InputStreamReader(new FileInputStream(langue), StandardCharsets.UTF_8)) {
			properties.load(is);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		for(final Field field: fields) {
			try {
				if("langue".equals(field.getName())) {
					continue;
				}
				final var property = (StringProperty) field.get(this);
				final var value = properties.getProperty(field.getName());
				property.set(value);
			} catch(final ClassCastException e ) {
				// gère les problème de cast (par exemple le fileManager n'est pas une propertie
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public File getLangue() {
		return langue;
	}

	public StringProperty getApplicationDejaEnCoursExceptionMessage() {
		return applicationDejaEnCoursExceptionMessage;
	}

	public StringProperty getApplicationDejaEnCoursExceptionDescription() {
		return applicationDejaEnCoursExceptionDescription;
	}

	public StringProperty getBadOsExceptionMessage() {
		return badOsExceptionMessage;
	}

	public StringProperty getBadOsExceptionDescription() {
		return badOsExceptionDescription;
	}

	public StringProperty getDataLoadingExceptionMessage() {
		return dataLoadingExceptionMessage;
	}

	public StringProperty getDataLoadingExceptionDescription() {
		return dataLoadingExceptionDescription;
	}

	public StringProperty getMenuItemAjouter() {
		return menuItemAjouter;
	}

	public StringProperty getMenuItemModifier() {
		return menuItemModifier;
	}

	public StringProperty getMenuItemSupprimer() {
		return menuItemSupprimer;
	}

	public StringProperty getMenuItemRegarder() {
		return menuItemRegarder;
	}

	public StringProperty getColonneNomLegende() {
		return colonneNomLegende;
	}

	public StringProperty getColonnePseudoLegende() {
		return colonnePseudoLegende;
	}

	public StringProperty getColonneIdLegende() {
		return colonneIdLegende;
	}

	public StringProperty getColonneInGameLegende() {
		return colonneInGameLegende;
	}

	public StringProperty getColonneServeurLegende() {
		return colonneServeurLegende;
	}

	public StringProperty getNomPlaceHolder() {
		return nomPlaceHolder;
	}

	public StringProperty getPseudoPlaceHolder() {
		return pseudoPlaceHolder;
	}

	public StringProperty getCommandeNonTrouveeExceptionMessage() {
		return commandeNonTrouveeExceptionMessage;
	}

	public StringProperty getCommandeNonTrouveeExceptionDescription() {
		return commandeNonTrouveeExceptionDescription;
	}

	public StringProperty getPlayerNotFoundExceptionMessage() {
		return playerNotFoundExceptionMessage;
	}

	public StringProperty getPlayerNotFoundExceptionDescription() {
		return playerNotFoundExceptionDescription;
	}

	public StringProperty getNoLolInstallationFoundMessage() {
		return noLolInstallationFoundMessage;
	}

	public StringProperty getNoLolInstallationFoundDescription() {
		return noLolInstallationFoundDescription;
	}

	public StringProperty getRegarderProcessExceptionMessage() {
		return regarderProcessExceptionMessage;
	}

	public StringProperty getRegarderProcessExceptionDescription() {
		return regarderProcessExceptionDescription;
	}

	public StringProperty getJoueurDejaPresentExceptionMessage() {
		return joueurDejaPresentExceptionMessage;
	}

	public StringProperty getJoueurDejaPresentExceptionDescription() {
		return joueurDejaPresentExceptionDescription;
	}

	public StringProperty getDataNotFoundExceptionMessage() {
		return dataNotFoundExceptionMessage;
	}

	public StringProperty getDataNotFoundExceptionDescription() {
		return dataNotFoundExceptionDescription;
	}

	public StringProperty getSauvegardeCorrompueExceptionMessage() {
		return sauvegardeCorrompueExceptionMessage;
	}

	public StringProperty getSauvegardeCorrompueExceptionDescription() {
		return sauvegardeCorrompueExceptionDescription;
	}

	public StringProperty getServiceCreationFailedExceptionMessage() {
		return serviceCreationFailedExceptionMessage;
	}

	public StringProperty getServiceCreationFailedExceptionDescription() {
		return serviceCreationFailedExceptionDescription;
	}

	public StringProperty getShowAlertFromHeader() {
		return showAlertFromHeader;
	}

	public StringProperty getShowAlertFromContext() {
		return showAlertFromContext;
	}

	public StringProperty getShowWarningAlertJoueursDejaPresentsHeader() {
		return showWarningAlertJoueursDejaPresentsHeader;
	}

	public StringProperty getShowWarningAlertJoueursDejaPresentsListe() {
		return showWarningAlertJoueursDejaPresentsListe;
	}

	public StringProperty getGetInputStreamFromResourceFileNotFound() {
		return getInputStreamFromResourceFileNotFound;
	}

	public StringProperty getTrayIconServiceQuitter() {
		return trayIconServiceQuitter;
	}

	public StringProperty getTrayIconServiceEnJeu() {
		return trayIconServiceEnJeu;
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		setLangue(fm.getFileFromResources("traductions/" + ps.get("default_language") + ".txt"));
	}
}
