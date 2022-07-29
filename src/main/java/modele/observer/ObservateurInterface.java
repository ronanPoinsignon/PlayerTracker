package modele.observer;

import modele.joueur.Serveur;

public interface ObservateurInterface {

	void notifyNewStringValueNom(String value);
	void notifyNewStringValuePseudo(String value);
	void notifyNewServerValue(Serveur value);
}