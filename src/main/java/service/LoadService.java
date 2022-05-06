package service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;
import service.exception.SauvegardeCorrompueException;

public class LoadService implements IService {
	private AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);
	
	public List<Joueur> load() {
		File fichier = null;
		List<Joueur> joueurs = new ArrayList<>();

		fichier = new File("joueurs.txt");
		try {
			fichier.createNewFile();
		} catch (IOException e) {
			alertService.alert(e);
		}
		
		if(fichier.length() == 0)
			return joueurs;
		
		try (FileInputStream is = new FileInputStream(fichier); ObjectInputStream ois = new ObjectInputStream(is)) {
			Joueur joueur = null;
			int size = ois.readInt();
			
			for(int i = 1; i <= size; i++) {
				joueur = (Joueur) ois.readObject();
				joueurs.add(joueur);
			}
			
			return joueurs;
			
		} catch (ClassNotFoundException|EOFException|StreamCorruptedException e) {
			alertService.alert(new SauvegardeCorrompueException());
			
			try {
				Files.delete(fichier.toPath());
			} catch (IOException e1) {
				alertService.alert(e1);
			}
			
			return new ArrayList<>();
		} catch (IOException e) {
			alertService.alert(e);
			return new ArrayList<>();
		}
	}
}
