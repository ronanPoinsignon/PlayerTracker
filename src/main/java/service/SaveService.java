package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;

public class SaveService implements IService {
	private List<Joueur> joueurs = new ArrayList<>();
	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);
	
	public void addJoueur(Joueur joueur) {
		joueurs.add(joueur);
		save();
	}
	
	public void removeJoueur(Joueur joueur) {
		joueurs.remove(joueur);
		save();
	}
	
	public void save() {		
		File fichier = null;

		fichier = new File("joueurs.txt");
		try {
			fichier.createNewFile();
		} catch (IOException e) {
			alertService.alert(e);
		}
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
			oos.writeInt(joueurs.size());
			
			for(Joueur joueur : joueurs) {
				if(joueur instanceof JoueurFx)
					joueur = ((JoueurFx) joueur).getJoueur();
				
				oos.writeObject(joueur);
			}
			
			oos.close();
		} catch (IOException e) {
			alertService.alert(e);
		}
				
	}

}
