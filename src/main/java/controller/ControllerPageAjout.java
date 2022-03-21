package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import modele.joueur.Joueur;
import modele.observable.Observer;

public class ControllerPageAjout implements Initializable {

	@FXML
	TextField textNom;

	@FXML
	TextField textPseudo;

	List<Observer> observers = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void addJoueur() {
		Joueur joueur = new Joueur(textPseudo.getText(), textNom.getText());
		observers.forEach(observer -> observer.addJoueur(joueur));
	}

	public void addObserver(Observer obs) {
		observers.add(obs);
	}

}
